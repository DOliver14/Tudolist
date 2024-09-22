package br.com.doliveertw.tudolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

 /*
     * 
     * ID
     * Usuario (ID_USUARIO)
     * Titulo
     * Data de inicio
     * Data de Termino
     * Prioridade
     */

@Data
@Entity(name="tb_tasks")
public class TaskModel {
  
     @Id
     @GeneratedValue(generator = "UUID")   //ESTA GERANDO UM ID AUTOMATICAMENTE 
     private  UUID id;
     private String descripton;

     @Column(length = 50)    //ESTA LIMITANDO O TAMANHO DE 50 CARACTERS
     private String title;
     private LocalDateTime startAt;
     private LocalDateTime endAt;
     private String priority;

     private UUID idUser;

     @CreationTimestamp
     private LocalDateTime createdAt;

     public void setTitle(String title) throws Exception{
          if(title.length() > 50){
               throw new Exception("O campo de titulo deve conter no maximo de 50 caracteres.");
          }

          this.title = title;
          
     }


}
