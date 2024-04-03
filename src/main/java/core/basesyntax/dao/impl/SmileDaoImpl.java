package core.basesyntax.dao.impl;

import java.util.ArrayList;
import java.util.List;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't create smile");
            }
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Transaction transaction = null;
        Session session = factory.openSession();
        try {
            transaction = session.beginTransaction();
            Smile smile = session.getReference(Smile.class, id);
            transaction.commit();
            return smile;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
                throw new RuntimeException("Can't get comment by id");
            }
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Smile> getAll() {
        Transaction transaction = null;
        List<Smile> smiles = new ArrayList<>();
        Session session = factory.openSession();
        try {
            transaction = session.getTransaction();
            transaction.begin();
            smiles = session.createQuery("FROM Smile", Smile.class).getResultList();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get all smiles");
        } finally {
            session.close();
        }
        return smiles;
    }
}
