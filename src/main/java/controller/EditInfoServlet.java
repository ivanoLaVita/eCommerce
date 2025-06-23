package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Base64.Encoder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.AddressBean;
import model.AddressDAO;
import model.PaymentMethodBean;
import model.PaymentMethodDAO;
import model.UsersBean;
import model.UsersDAO;

@WebServlet("/info")
public class EditInfoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public EditInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String target = request.getParameter("target");
		String username = (String) request.getSession().getAttribute("utente");
		String pathRedirect = null;
		
		
		if(mode.equals("update")) {
			if(target.equals("utente")) {
				UsersDAO dbUtenti = new UsersDAO();
				 UsersBean utente = new UsersBean();
				 
				 try {
					utente = dbUtenti.doRetrieveByUsername(username);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				request.setAttribute("utente", utente);
				request.setAttribute("target", "utente");
				pathRedirect = "./memberArea.jsp";
			}
		} else if (mode.equals("add")) {
			String utente = (String) request.getSession().getAttribute("email");
			request.setAttribute("utente", utente);
			if(target.equals("indirizzo")) {
				request.setAttribute("target", "indirizzo");
				pathRedirect = "./aggiungiInfoForm.jsp";
			} else if (target.equals("metodoPagamento")) {
				request.setAttribute("target", "metodoPagamento");
				pathRedirect = "./aggiungiInfoForm.jsp";
			}
		}
		RequestDispatcher view = request.getRequestDispatcher(pathRedirect);
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = null;
        String mode = request.getParameter("mode");
        String target = request.getParameter("target");
        boolean flag = false;
		UsersDAO dbUtenti = new UsersDAO();

        if (mode != null && mode.equalsIgnoreCase("update")) {
            if (target != null && target.equalsIgnoreCase("utente")) {
                UsersDAO dbUtente = new UsersDAO();
                UsersBean utente = new UsersBean();
                Encoder encoder = Base64.getEncoder();
                
                String username = (String) request.getSession().getAttribute("utente");
                String newUsername = request.getParameter("usernameNuovo");
                String password = request.getParameter("password");
				String passwordCheck = request.getParameter("passwordCheck");
                String email = request.getParameter("emailNuovo");
                String nome = request.getParameter("nomeNuovo");
                String cognome = request.getParameter("cognomeNuovo");
                String pwd64 = null;
                
                if(password.equals(passwordCheck)) {
                	pwd64 = encoder.encodeToString(password.getBytes());
					try {
						utente = dbUtente.doRetrieveByUsername(username);

						if (!utente.getUsername().equals(newUsername)) {
	                        utente.setUsername(newUsername);
	                        request.getSession().setAttribute("utente", newUsername);
	                    }
						
						if(!utente.getPassword().equals(pwd64)) {
							utente.setPassword(pwd64);
							request.getSession().setAttribute("password", pwd64);
						}
						
						if(!utente.getEmail().equals(email)) {
							utente.setEmail(email);
							request.getSession().setAttribute("email", email);
						}
						
						if(!utente.getEmail().equalsIgnoreCase(email)) {
							List<UsersBean> listaUtenti = dbUtente.doRetrieveAll("");
							Iterator<UsersBean> iterUtenti = listaUtenti.iterator();
							UsersBean utenteRicercato = new UsersBean();
							while(iterUtenti.hasNext()) {
								utenteRicercato = iterUtenti.next();
								
								if(!utente.getUsername().equalsIgnoreCase(utenteRicercato.getUsername())) {
									if(utenteRicercato.getEmail().equalsIgnoreCase(email)) {
										flag = true;
										break;
									}
								}
							}
							utente.setEmail(email);
							request.getSession().setAttribute("email", email);
						}
						
						if (!utente.getFirstName().equals(nome)) {
	                        utente.setFirstName(nome);
	                        request.getSession().setAttribute("nome", nome);
	                    }

	                    if (!utente.getLastName().equals(cognome)) {
	                        utente.setLastName(cognome);
	                        request.getSession().setAttribute("cognome", cognome);
	                    }
	                    
	                    if(!flag) {
	                    	if (!dbUtente.doUpdate(utente, email)) {
	                            request.getSession().setAttribute("error", "Aggiornamento non effettuato!");
	                            path = "modificaInfo?mode=update&target=utente";
	                        } else {
	                            request.getSession().setAttribute("message", "Aggiornato con successo!");
	                            request.getSession().setAttribute("utente", utente.getUsername());
	                			path = "./memberArea.jsp";
	                        }
	                    } else {
							request.getSession().setAttribute("error", "Impossibile usare l'email scelta. Riprova.");
							path = "./modificaInfo?mode=update&target=utente&utente=" + username;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
                }
          
            }
        } else if(mode.equals("add")) {
        	String utente = request.getParameter("utente");
        	if(target.equals("indirizzo")) {
        		AddressBean indirizzo = new AddressBean();
				AddressDAO dbIndirizzo = new AddressDAO();
				
				String via = request.getParameter("via");
				String citta = request.getParameter("citta");
				String CAP = request.getParameter("CAP");
				String civico = request.getParameter("civico");
				String provincia = request.getParameter("provincia");
				
				indirizzo.setUtenteEmail(utente);
				indirizzo.setStreet(via);
				indirizzo.setCity(citta);
				indirizzo.setPostalCode(CAP);
				indirizzo.setStreetNumber(civico);
				indirizzo.setProvince(provincia);
				
				try {
					dbIndirizzo.doSave(indirizzo);
					request.getSession().setAttribute("message", "Aggiunto nuovo indirizzo con successo!");
					path = "./memberArea.jsp";
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	} else if(target.equals("metodoPagamento")) {
        		PaymentMethodBean metodoPagamento = new PaymentMethodBean();
        		PaymentMethodDAO dbMetodiPagamento = new PaymentMethodDAO();
				
				String tipo = request.getParameter("tipo");
				String IBAN = request.getParameter("iban");
				String numCarta = request.getParameter("carta");
				
				metodoPagamento.setUtenteEmail(utente);
				metodoPagamento.setType(tipo);
				if (tipo.equals("iban")) {
					if (IBAN != null) {
						metodoPagamento.setIban(IBAN);
					} else {
						request.getSession().setAttribute("error", "Aggiunta non effettuata!");
						path = "modificaInfo?mode=add&target=metodoPagamento&utente=" + utente;
					}
				} else if (tipo.equals("carta")) {
					if (numCarta != null) {
						metodoPagamento.setCardNumber(numCarta);
					} else {
						request.getSession().setAttribute("error", "Aggiunta non effettuata!");
						path = "modificaInfo?mode=add&target=metodoPagamento&utente=" + utente;
					}
				}
				try {
					dbMetodiPagamento.doSave(metodoPagamento);
					request.getSession().setAttribute("message", "Aggiunto nuovo metodo di pamento con successo!");
					path = "./memberArea.jsp";
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        } else if(mode.equals("checkEmail")) {
			response.setContentType("text/plain");
			String email = request.getParameter("email");
			
			try {
				if(dbUtenti.checkEmail(email)) {
					response.getWriter().print("non disponibile");
				} else {
					response.getWriter().print("disponibile");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(mode.equalsIgnoreCase("checkUsername")) {
			response.setContentType("text/plain");
			String username = request.getParameter("username");
			
			try {
				if(dbUtenti.checkUsername(username)) {
					response.getWriter().print("non disponibile");
				} else {
					response.getWriter().print("disponibile");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        
        response.sendRedirect(path);
    }
}
