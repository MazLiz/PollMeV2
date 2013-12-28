package com.example.googletutorial2;

import com.example.googletutorial2.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "utenteendpoint", namespace = @ApiNamespace(ownerDomain = "example.com", ownerName = "example.com", packagePath = "googletutorial2"))
public class UtenteEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUtente")
	public CollectionResponse<Utente> listUtente(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Utente> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Utente as Utente");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Utente>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Utente obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Utente> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUtente")
	public Utente getUtente(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Utente utente = null;
		try {
			utente = mgr.find(Utente.class, id);
		} finally {
			mgr.close();
		}
		return utente;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param utente the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUtente")
	public Utente insertUtente(Utente utente) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUtente(utente)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(utente);
		} finally {
			mgr.close();
		}
		return utente;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param utente the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUtente")
	public Utente updateUtente(Utente utente) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUtente(utente)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(utente);
		} finally {
			mgr.close();
		}
		return utente;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUtente")
	public void removeUtente(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Utente utente = mgr.find(Utente.class, id);
			mgr.remove(utente);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUtente(Utente utente) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Utente item = mgr.find(Utente.class, utente.getEmail());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
