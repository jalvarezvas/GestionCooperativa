/**
 * 
 */
package com.pericles.cooperativa.gestion.auditor;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareBean implements AuditorAware<String> {
	public String getCurrentAuditor() {
		return "user_developer"; // TODO: Ver SecurityContextHolder cuando se
								// implemente seguridad
	}
}

// class SpringSecurityAuditorAware implements AuditorAware<User> {
//
// public User getCurrentAuditor() {
//
// Authentication authentication = SecurityContextHolder.getContext()
// .getAuthentication();
//
// if (authentication == null || !authentication.isAuthenticated()) {
// return null;
// }
//
// return ((MyUserDetails) authentication.getPrincipal()).getUser();
// }
// }