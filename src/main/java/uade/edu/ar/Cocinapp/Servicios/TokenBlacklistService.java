package uade.edu.ar.Cocinapp.Servicios;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistService {
    private Set<String> tokensInvalidos = ConcurrentHashMap.newKeySet();

    public void invalidarToken(String token) {
        tokensInvalidos.add(token);
    }

    public boolean esTokenInvalido(String token) {
        return tokensInvalidos.contains(token);
    }
}

