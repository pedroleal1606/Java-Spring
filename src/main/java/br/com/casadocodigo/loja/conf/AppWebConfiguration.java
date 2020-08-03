package br.com.casadocodigo.loja.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDao.class, FileSaver.class, CarrinhoCompras.class })
public class AppWebConfiguration {

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {

		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");

		resolver.setExposedContextBeanNames("carrinhoCompras");

		return resolver;

	}

	@Bean
	public MessageSource ms() {

		ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();

		ms.setBasename("/WEB-INF/messages");
		ms.setDefaultEncoding("UTF-8");
		ms.setCacheSeconds(1);

		return ms;

	}

	@Bean
	public FormattingConversionService mvcConversionService() {

		DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService();
		DateFormatterRegistrar dfr = new DateFormatterRegistrar();
		dfr.setFormatter(new DateFormatter("dd/MM/yyyy"));
		dfr.registerFormatters(dfcs);

		return dfcs;
	}

	@Bean
	public MultipartResolver multipartResolver() {

		return new StandardServletMultipartResolver();

	}

	@Bean
	public RestTemplate restTemplate() {

		return new RestTemplate();

	}
}
