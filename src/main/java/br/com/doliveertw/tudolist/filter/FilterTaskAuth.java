package br.com.doliveertw.tudolist.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.doliveertw.tudolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component //TODA A CLASSE QUE EU QUERO QUE O SPRING GERENCIE 
public class FilterTaskAuth extends OncePerRequestFilter {
   
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();
                System.out.println("PATH: " + servletPath);
                if(servletPath.startsWith("/tasks/")){
                    //PEGR A AUTENTICAÇAO (usuario e senha )
                    var authorization = request.getHeader("Authorization");

                    var authEncoded = authorization.substring("basic".length()).trim();

                    byte[] authDecode = java.util.Base64.getDecoder().decode(authEncoded);

                    var authString = new String(authDecode);
                

                    String[] credentials = authString.split(":");
                    String username = credentials[0];
                    String password = credentials[1];
                
                    //Validar usuario

                    var user = this.userRepository.findByUsername(username);
                    if(user == null) {
                        response.sendError(401);
                    }else{
                        //validar senha
                        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                        if(passwordVerify.verified){
                             //segue viagem
                            request.setAttribute("idUser", user.getId());
                            filterChain.doFilter(request, response);
                        }else{
                            response.sendError(401);
                        }
                       
                    }
                }else{
                    filterChain.doFilter(request, response);
                }
         
                

                
    }


    
}
