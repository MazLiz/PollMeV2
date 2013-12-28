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

@Api(name = "amiciziaendpoint", namespace = @ApiNamespace(ownerDomain = "example.com", ownerName = "example.com", packagePath = "googletutorial2"))
public class AmiciziaEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listAmicizia")
	public CollectionResponse<Amicizia> listAmicizia(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Amicizia> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Amicizia as Amicizia");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Amicizia>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Amicizia obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Amicizia> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getAmicizia")
	public Amicizia getAmicizia(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Amicizia amicizia = null;
		try {
			amicizia = mgr.find(Amicizia.class, id);
		} finally {
			mgr.close();
		}
		return amicizia;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param amicizia the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertAmicizia")
	public Amicizia insertAmicizia(Amicizia amicizia) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsAmicizia(amicizia)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(amicizia);
		} finally {
			mgr.close();
		}
		return amicizia;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param amicizia the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateAmicizia")
	public Amicizia updateAmicizia(Amicizia amicizia) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsAmicizia(amicizia)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(amicizia);
		} finally {
			mgr.close();
		}
		return amicizia;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeAmicizia")
	public void removeAmicizia(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Amicizia amicizia = mgr.find(Amicizia.class, id);
			mgr.remove(amicizia);
		} finally {
			mgr.close();
		}
	}

	private boolean containsAmicizia(Amicizia amicizia) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Amicizia item = mgr.find(Amicizia.class, amicizia.getKeyA());
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
