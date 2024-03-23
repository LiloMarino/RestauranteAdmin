package group.Prova1AdminMuriloMarino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemCardapioController {
    @Autowired
    ItemCardapioRepository itemCardapioRepository;

    @GetMapping("/novo-cardapio")
    public String mostrarNovoCardapio(ItemCardapio cardapio){
        return "novo-cardapio";
    }


}
