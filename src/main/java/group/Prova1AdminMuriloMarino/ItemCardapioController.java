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
public class ItemCardapioController {
    @Autowired
    ItemCardapioRepository itemCardapioRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    @GetMapping("/cardapio/{id}")
    public String mostrarCardapioRestaurante(@PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        Iterable<ItemCardapio> cardapios = itemCardapioRepository.findByRestauranteId(id);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("cardapios", cardapios.iterator().hasNext() ? cardapios : null);
        return "cardapio";
    }

    @GetMapping("/novo-cardapio/{id}")
    public String mostrarNovoCardapio(ItemCardapio cardapio, @PathVariable("id") int id, Model model) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("cardapio", cardapio); // Porque eu tive que fazer isso?
        return "novo-cardapio";
    }

    @GetMapping("/editar/cardapio/{id}")
    public String mostrarEditarCardapio(@PathVariable("id") int id, Model model) {
        ItemCardapio cardapio = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));

        model.addAttribute("cardapio", cardapio);
        return "editar-cardapio";
    }

    @GetMapping("/remover/cardapio/{id}")
    public String removerCardapio(@PathVariable("id") int id) {
        ItemCardapio cardapio = itemCardapioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        int idRestaurante = cardapio.getRestaurante().getId();
        itemCardapioRepository.delete(cardapio);
        return "redirect:/cardapio/" + idRestaurante;
    }

    @PostMapping("/adicionar-cardapio/{id}")
    public String novoCardapio(@PathVariable("id") int id, @Valid ItemCardapio cardapio, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/novo-cardapio/" + id;
        }
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        cardapio.setRestaurante(restaurante);

        itemCardapioRepository.save(cardapio);
        return "redirect:/cardapio/" + id;
    }

    @PostMapping("/atualizar/cardapio/{id}/restaurante/{idRestaurante}")
    public String atualizarCardapio(@PathVariable("id") int id, @PathVariable("idRestaurante") int idRestaurante,
            @Valid ItemCardapio itemCardapioAtualizado,
            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/editar/cardapio/" + id;
        }
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new IllegalArgumentException("ID:" + id));
        itemCardapioAtualizado.setRestaurante(restaurante);
        itemCardapioRepository.save(itemCardapioAtualizado);
        return "redirect:/cardapio/" + itemCardapioAtualizado.getRestaurante().getId();
    }

}
