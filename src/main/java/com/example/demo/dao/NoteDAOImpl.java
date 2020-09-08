package com.example.demo.dao;

import com.example.demo.model.Note;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class NoteDAOImpl implements NoteDAO {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Note> allNotes(int page, int rowPerPage, String filter) {
        Session session = sessionFactory.getCurrentSession();
        Boolean filterValue = parseFilterValue(filter);
        Query query;

        if (filter.equals("all")) {
            query = session.createQuery("from Note");
        } else {
            query = session.createQuery("from Note where completed=:queryFilter");
            query.setParameter("queryFilter", filterValue);
        }
        return query.setFirstResult(rowPerPage * (page)).setMaxResults(rowPerPage).list();
    }

    @Override
    public int notesCount(String filter) {
        Session session = sessionFactory.getCurrentSession();
        Boolean filterValue = parseFilterValue(filter);
        int count;

        if (filter.equals("all")) {
            count = session.createQuery("select count(*) from Note", Number.class).getSingleResult().intValue();
        } else {
            count = session.createQuery("select count(*) from Note where completed=:queryFilter", Number.class)
                .setParameter("queryFilter", filterValue)
                .getSingleResult().intValue();
        }

        return count;
    }

    private Boolean parseFilterValue(String filter) {
        Boolean queryFilter = false;

        if (filter.equals("todo")) {
            queryFilter = false;
        } else if (filter.equals("done")) {
            queryFilter = true;
        }
        return queryFilter;
    }

    @Override
    public void add(Note note) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(note);
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Note.class, id));
    }

    @Override
    public void edit(Note note) {
        Session session = sessionFactory.getCurrentSession();
        session.update(note);
    }

    @Override
    public Note getById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Note.class, id);
    }
}
