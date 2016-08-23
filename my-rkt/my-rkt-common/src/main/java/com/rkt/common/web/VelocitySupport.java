package com.rkt.common.web;
import com.rkt.common.web.exception.WebException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class VelocitySupport {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(VelocitySupport.class);

	// Velocity参数
	protected final static String VELOCITY_DETAULT_LAYOUT = "layout/default";
	protected final static String VELOCITY_ERROR_VIEW = "common/error";
	protected final static String VELOCITY_VM_SUFFIX = ".vm";
	protected final static String VELOCITY_SCREEN_CONTENT = "screen_content";
	protected final static String VELOCITY_DEFAULT_MODEL_KEY = "model";

	@Autowired
	private VelocityConfigurer velocityConfigurer;

	@Resource(name = "velocityTools")
	private Map<String, Object> velocityTools;

	/**
	 * 输出VM模板,跳过布局
	 * 
	 * @param view
	 *            视图
	 * @parammodel
	 *            数据
	 * @return
	 */
	protected ModelAndView toVMSkipLayout(String view) {
		return toVMSkipLayout(view, null);
	}

	/**
	 * 输出VM模板,跳过布局
	 * 
	 * @param view
	 *            视图
	 * @param model
	 *            数据
	 * @return
	 */
	protected ModelAndView toVMSkipLayout(String view, Object model) {
		return toVM(null, view, model);
	}

	/**
	 * 输出VM模板,默认布局
	 * 
	 * @param view
	 *            视图
	 * @return
	 */
	protected ModelAndView toVM(String view) {
		return toVM(view, null);
	}

	/**
	 * 输出VM模板,默认布局
	 * 
	 * @param view
	 *            视图
	 * @param model
	 *            数据
	 * @return
	 */
	protected ModelAndView toVM(String view, Object model) {
		return toVM(VELOCITY_DETAULT_LAYOUT, view, model);
	}

	/**
	 * 输出VM模板,自定义布局
	 * 
	 * @param layout
	 *            布局
	 * @param view
	 *            视图
	 * @param model
	 *            数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected ModelAndView toVM(String layout, String view, Object model) {
		VelocityEngine engine = velocityConfigurer.getVelocityEngine();

		// 判断模板是否存在
		String viewVM = view + VELOCITY_VM_SUFFIX;
		boolean exists = engine.resourceExists(viewVM);
		if (!exists) {
			throw new WebException("未找到指定视图[" + view + "].");
		}

		Map<String, Object> context = getDefaultContext();
		if (context == null) {
			context = new HashMap<String, Object>();
		}

		// 添加默认数据
		if (model instanceof Map) {
			context.putAll((Map<String, Object>) model);
		} else {
			context.put(VELOCITY_DEFAULT_MODEL_KEY, model);
		}

//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("process vm==============" + viewVM);
//		}

		// 设置velocityUtils变量
		if (velocityTools != null && !velocityTools.isEmpty()) {
			context.putAll(velocityTools);
		}

		if (layout == null) {// 跳过模板直接输出
			return new ModelAndView(view, context);
		}

		// 判断布局模板是否存在
		exists = engine.resourceExists(layout + VELOCITY_VM_SUFFIX);
		if (!exists) {
			throw new WebException("未找到指定布局模板[" + layout + "].");
		}

		// 输入输出编码
		Template bodyVM = engine.getTemplate(viewVM,
				(String) engine.getProperty(VelocityEngine.INPUT_ENCODING));
		// 合并screen_content
		StringWriter screenContent = new StringWriter();
		bodyVM.merge(new VelocityContext(context), screenContent);

		// 添加到默认布局中
		context.put(VELOCITY_SCREEN_CONTENT, screenContent.toString());

		return new ModelAndView(layout, context);
	}

	/**
	 * velocity默认数据
	 * 
	 * @return
	 */
	protected abstract Map<String, Object> getDefaultContext();
}
