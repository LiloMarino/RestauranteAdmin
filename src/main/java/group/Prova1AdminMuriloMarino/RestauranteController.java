package group.Prova1AdminMuriloMarino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class RestauranteController {
    @Autowired
    RestauranteRepository restauranteRepository;

    @GetMapping(value = { "/index", "/" })
    public String mostrarRestaurantes(Model model) {
        Iterable<Restaurante> restaurantes = restauranteRepository.findAll();
        model.addAttribute("restaurantes", restaurantes.iterator().hasNext() ? restaurantes : null);
        return "restaurantes";
    }

    @GetMapping("/novo-restaurante")
    public String mostrarNovoRestaurante(Restaurante restaurante) {
        return "novo-restaurante";
    }

    @GetMapping("/editar/restaurante/{id}")
    public String mostrarEditarRestaurante(@PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));

        model.addAttribute("restaurante", restaurante);
        return "editar-restaurante";
    }

    @GetMapping("/remover/restaurante/{id}")
    public String removerRestaurante(@PathVariable("id") int id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        restauranteRepository.delete(restaurante);
        return "redirect:/index";
    }

    @PostMapping("/adicionar-restaurante")
    public String novoRestaurante(@Valid Restaurante restaurante, BindingResult result) {
        if (result.hasErrors()) {
            return "/novo-restaurante";
        }

        restauranteRepository.save(restaurante);
        return "redirect:/index";
    }

    @PostMapping("/atualizar/restaurante/{id}")
    public String atualizarRestaurante(@PathVariable("id") int id,
            @Valid Restaurante restauranteAtualizado,
            BindingResult result) {
        if (result.hasErrors()) {
            restauranteAtualizado.setId(id);
            return "editar-restaurante";
        }

        restauranteRepository.save(restauranteAtualizado);
        return "redirect:/index";
    }

}
