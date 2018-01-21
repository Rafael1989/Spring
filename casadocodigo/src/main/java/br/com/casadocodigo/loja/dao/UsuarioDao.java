package br.com.casadocodigo.loja.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.model.Usuario;

@Repository
public class UsuarioDao implements UserDetailsService{

	@PersistenceContext
	private EntityManager em;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = em.createQuery("select u from Usuario u where u.email = :email", Usuario.class).setParameter("email",email).getSingleResult();
		
		if(usuario == null) {
			throw new UsernameNotFoundException("O usuário "+ email + " não foi encontrado");
		}
		
		return usuario;
	}
	
	public Usuario getUsuario(Usuario usuario) {
		try {
			return em.createQuery("select u from Usuario u where u.email = :email", Usuario.class)
					.setParameter("email", usuario.getEmail()).getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
	
	public void adiciona(Usuario usuario) {
		em.persist(usuario);
	}
}
