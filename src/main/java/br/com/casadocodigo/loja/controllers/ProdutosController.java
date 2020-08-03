package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validacions.ProdutoValidation;

@Controller
@RequestMapping("produtos")
public class ProdutosController {

	@Autowired
	private ProdutoDao pDao;

	@Autowired
	private FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder wdb) {

		wdb.addValidators(new ProdutoValidation());

	}

	@RequestMapping("/form")
	public ModelAndView form(Produto p) {

		ModelAndView mav = new ModelAndView("produtos/form");
		mav.addObject("tipos", TipoPreco.values());

		return mav;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto p, BindingResult br, RedirectAttributes ra) {

		if (br.hasErrors())
			return form(p);

		String path = fileSaver.write("arquivos-sumario", sumario);
		p.setSumarioPath(path);

		pDao.gravar(p);

		ra.addFlashAttribute("sucesso", "Cadastro feito com sucesso");

		return new ModelAndView("redirect:produto");
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listar() {

		List<Produto> produtos = pDao.listar();
		ModelAndView mav = new ModelAndView("produtos/lista");
		mav.addObject("produtos", produtos);

		return mav;
	}

	@RequestMapping("/detalhe/{id}")
	public ModelAndView detalhe(@PathVariable("id") Integer id) {

		ModelAndView mav = new ModelAndView("produtos/detalhe");

		Produto produto = pDao.find(id);
		mav.addObject("produto", produto);

		return mav;

	}

}
