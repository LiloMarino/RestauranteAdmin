package group.Prova1AdminMuriloMarino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String mostrarNovoRestaurante() {
        return "novo-restaurante";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditarRestaurante(@PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ID:" + id));

        model.addAttribute("restaurante", restaurante);
        return  "editar-restaurante";
    }

    @GetMapping("/cardapio/{id}")
    public String mostrarCardapioRestaurante(@PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ID:" + id));

        model.addAttribute("restaurante", restaurante);
        return "cardapio";
    }

    @GetMapping("/remover/{id}")
    public String removerRestaurante(@PathVariable("id") int id) {
        Restaurante restaurante = restauranteRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        restauranteRepository.delete(restaurante);
        return "redirect:/index";
    }

}
