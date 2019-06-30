package br.com.dbc.wallet.Security;

import br.com.dbc.wallet.Entity.Usuario;
import br.com.dbc.wallet.Enuns.Situacao;
import br.com.dbc.wallet.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service("userDetailsService")
public class UserDetailsImplementacao implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin( login );

        if( usuario == null || usuario.getSituacao() == Situacao.INATIVO){
            throw new UsernameNotFoundException( login + "não registrado ou inativo!");
        }

        List<GrantedAuthority> permissoes = Stream
                .of( new SimpleGrantedAuthority( usuario.getSituacao().toString() ))
                .collect( toList() );

        return new User( usuario.getLogin(), usuario.getSenha(), permissoes);
    }
}
