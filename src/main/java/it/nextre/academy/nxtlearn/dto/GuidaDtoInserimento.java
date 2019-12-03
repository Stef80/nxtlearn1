package it.nextre.academy.nxtlearn.dto;

import it.nextre.academy.nxtlearn.model.Guida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class GuidaDtoInserimento {
        private String nome;
        private String descrizione;
        private Integer difficolta ;
        public GuidaDtoInserimento(Guida g){
            this.nome=g.getNome();
            this.descrizione=g.getDescrizione();
            this.difficolta = g.getLivello().getDifficolta();
        }


    }// end Class
