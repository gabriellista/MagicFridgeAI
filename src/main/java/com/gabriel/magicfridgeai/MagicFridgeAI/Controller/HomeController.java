package com.gabriel.magicfridgeai.MagicFridgeAI.Controller;

import com.gabriel.magicfridgeai.MagicFridgeAI.Service.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.gabriel.magicfridgeai.MagicFridgeAI.Model.FoodItem;
import com.gabriel.magicfridgeai.MagicFridgeAI.Model.CategoriaAlimento;
import com.gabriel.magicfridgeai.MagicFridgeAI.Model.UnidadeMedida;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.gabriel.magicfridgeai.MagicFridgeAI.Service.ReceitaService;

@Controller
public class HomeController {

    private final FoodItemService foodItemService;
    private final ReceitaService receitaService;

    public HomeController(FoodItemService foodItemService, ReceitaService receitaService) {
        this.foodItemService = foodItemService;
        this.receitaService = receitaService;
    }

    @GetMapping("/")
    public String paginaInicial(Model model) {
        model.addAttribute("alimentos", foodItemService.listarTodos());
        model.addAttribute("novoAlimento", new FoodItem());
        model.addAttribute("categorias", CategoriaAlimento.values());
        model.addAttribute("unidades", UnidadeMedida.values());

        return "index";
    }

    @PostMapping("/alimentos/salvar")
    public String salvarAlimento(
            @Valid @ModelAttribute("novoAlimento") FoodItem foodItem,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("alimentos", foodItemService.listarTodos());
            model.addAttribute("categorias", CategoriaAlimento.values());
            model.addAttribute("unidades", UnidadeMedida.values());

            return "index";
        }
        foodItemService.salvar(foodItem);

        return "redirect:/";
    }

    @PostMapping("/alimentos/excluir/{id}")
    public String excluirAlimento(@PathVariable Long id) {
        foodItemService.excluir(id);

        return "redirect:/";
    }

    @PostMapping("/receita/gerar")
    public String gerarReceita(Model model) {
        try {
            String receita = receitaService.gerarReceita().block();
            model.addAttribute("receita", receita);
        } catch (RuntimeException exception) {
            model.addAttribute(
                    "erroReceita",
                    "Não foi possível gerar a receita no momento. Tente novamente mais tarde."
            );
        }
        model.addAttribute("alimentos", foodItemService.listarTodos());
        model.addAttribute("novoAlimento", new FoodItem());
        model.addAttribute("categorias", CategoriaAlimento.values());
        model.addAttribute("unidades", UnidadeMedida.values());
        return "index";
    }

    @GetMapping("/alimentos/editar/{id}")
    public String editarAlimento(@PathVariable Long id, Model model) {
        FoodItem alimento = foodItemService.buscarPorId(id).orElse(null);
        if (alimento == null) {
            return "redirect:/";
        }
        model.addAttribute("novoAlimento", alimento);
        model.addAttribute("alimentos", foodItemService.listarTodos());
        model.addAttribute("categorias", CategoriaAlimento.values());
        model.addAttribute("unidades", UnidadeMedida.values());

        return "index";
    }
}