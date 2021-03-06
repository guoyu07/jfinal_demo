package com.hxgsn.demo;

import com.hxgsn.handler.Handler1;
import com.hxgsn.handler.Handler2;
import com.hxgsn.interceptor.Interceptor1;
import com.hxgsn.interceptor.Interceptor2;
import com.hxgsn.model.UserModel;
import com.hxgsn.render.freemarker.ArticlesTag;
import com.jfinal.config.*;
import com.jfinal.ext.handler.FakeStaticHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.jfinal.render.IMainRenderFactory;
import com.jfinal.render.Render;
import com.jfinal.render.TextRender;
import com.jfinal.render.ViewType;
import com.jfinal.token.ITokenCache;
import com.jfinal.token.Token;

import java.util.List;

/**
 * Created by zgc on 16-7-13.
 */
public class DemoConfig extends JFinalConfig {

    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setBaseDownloadPath("download");//设置文件渲染的默认路径
        me.setBaseUploadPath("upload");//设置文件上传的默认路径
//        me.setBaseViewPath("");//设置路由视图的默认路径
//        me.setEncoding("utf-8");//设置jfinal编码
//        me.setError401View("");//设置401错误的视图
        me.setError404View("404.ftl");//设置404错误的视图
//        me.setErrorRenderFactory(new MyErrorRenderFactory());//设置错误视图工厂
//        me.setErrorView(401,"");//设置401错误的视图
//        me.setFreeMarkerTemplateUpdateDelay(0);//设置freemarker模板引擎的更新时间
        me.setFreeMarkerViewExtension("ftl");//设置freemarker的默认后缀，默认.html
//        me.setI18nDefaultBaseName("");//设置国际化的默认名称
//        me.setI18nDefaultLocale("");//设置国际化默认的语言
//        me.setJsonFactory(null);//设置json工厂
//        me.setJspViewExtension("");//设置jsp的默认后缀，默认.html
//        me.setMainRenderFactory(new IMainRenderFactory() {
//            @Override
//            public Render getRender(String view) {
//                return new TextRender("自定义RenderFactory...");
//            }
//
//            @Override
//            public String getViewExtension() {
//                return null;
//            }
//        });//设置渲染视图类
        me.setMaxPostSize(1024 * 1024 * 100);//设置post请求大小
        me.setReportAfterInvocation(false);//设置log打印顺序
//        me.setTokenCache(new ITokenCache() {
//            @Override
//            public void put(Token token) {
//                System.out.println(">>>>>>put:" + token.getId());
//            }
//
//            @Override
//            public void remove(Token token) {
//
//            }
//
//            @Override
//            public boolean contains(Token token) {
//                System.out.println(">>>>>>contains:" + token.getId());
//                return false;
//            }
//
//            @Override
//            public List<Token> getAll() {
//                return null;
//            }
//        });//设置token缓存
//        me.setUrlParaSeparator("-");//设置url参数分割符
//        me.setVelocityViewExtension("");//设置velocity的默认后缀，默认.html
//        me.setViewType(ViewType.FREE_MARKER);//设置视图类型
//        me.setXmlRenderFactory(null);//设置xml渲染器工程

    }

    public void configRoute(Routes me) {
        me.add("/hello", HelloController.class);
        me.add("/", IndexController.class);
        me.add("/user", UserController.class);
        me.add("/render", RenderController.class);

        me.add("/test", TestController.class, "test/index");
    }

    public void configPlugin(Plugins me) {
        me.add(new EhCachePlugin());

        DruidPlugin druidPlugin = new DruidPlugin("jdbc:postgresql://localhost:5432/dev", "dev", "dev");
        me.add(druidPlugin);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
        activeRecordPlugin.addMapping("tb_user", UserModel.class);
        activeRecordPlugin.setShowSql(true);
        activeRecordPlugin.setDialect(new PostgreSqlDialect());
//        activeRecordPlugin.setCache(new ICache() {
//            @Override
//            public <T> T get(String cacheName, Object key) {
//                return null;
//            }
//
//            @Override
//            public void put(String cacheName, Object key, Object value) {
//
//            }
//
//            @Override
//            public void remove(String cacheName, Object key) {
//
//            }
//
//            @Override
//            public void removeAll(String cacheName) {
//
//            }
//        });
        me.add(activeRecordPlugin);
    }

    public void configInterceptor(Interceptors me) {
        me.add(new Interceptor1());
//        me.add(new Interceptor2());
    }

    public void configHandler(Handlers me) {
//        me.add(handler);
//        me.add(new FakeStaticHandler());
//        me.add(new Handler1());
//        me.add(new Handler2());
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();

        FreeMarkerRender.getConfiguration().setSharedVariable("aTag", new ArticlesTag());
    }
}