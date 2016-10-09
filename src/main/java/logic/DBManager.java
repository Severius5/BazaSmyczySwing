package logic;

import entity.Leash;
import org.hibernate.query.Query;
import utils.Factory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DBManager {

    public Long add(final Leash leash) {
        Transaction transaction = null;

        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            Long ID = (Long) session.save(leash);

            transaction.commit();
            return ID;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public void edit(final Leash leash) {
        Transaction transaction = null;

        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            Leash leashFromDB = session.get(Leash.class, leash.getID());

            leashFromDB.setImageName(leash.getImageName());
            leashFromDB.setText(leash.getText());
            leashFromDB.setSize(leash.getSize());
            leashFromDB.setColor(leash.getColor());
            leashFromDB.setDesc(leash.getDesc());

            session.update(leashFromDB);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }

    }

    public void delete(final long leashID) {
        Transaction transaction = null;

        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            Leash leash = session.get(Leash.class, leashID);
            session.delete(leash);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Leash> getLeashes() {
        Transaction transaction = null;
        List<Leash> leashes = null;
        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            leashes = session.createQuery("FROM Leash").list();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return leashes;
    }

    public List<Leash> search(final Leash leash) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            Criteria cr = session.createCriteria(Leash.class);

            if (!leash.getImageName().isEmpty())
                cr.add(Restrictions.ilike("imageName", leash.getImageName(), MatchMode.ANYWHERE));
            if (!leash.getText().isEmpty())
                cr.add(Restrictions.ilike("text", leash.getText(), MatchMode.ANYWHERE));
            if (!leash.getSize().isEmpty())
                cr.add(Restrictions.ilike("size", leash.getSize(), MatchMode.ANYWHERE));
            if (!leash.getColor().isEmpty())
                cr.add(Restrictions.ilike("color", leash.getColor(), MatchMode.ANYWHERE));
            if(!leash.getDesc().isEmpty())
                cr.add(Restrictions.ilike("desc", leash.getDesc(), MatchMode.ANYWHERE));

            cr.addOrder(Order.asc("ID"));

            List<Leash> dbResults = cr.list();

            transaction.commit();
            return dbResults;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public boolean isImageNameExists(final Leash leash) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            Query query = session.createQuery("FROM Leash WHERE imageName = :imageName");
            query.setParameter("imageName", leash.getImageName());
            List<Leash> dbResults = query.list();

            transaction.commit();
            return !dbResults.isEmpty();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLeashExists(final Leash leash) {
        Transaction transaction = null;
        try (Session session = Factory.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();

            final String dbQuery = "FROM Leash WHERE imageName=:imageName and text=:text and "
                    +"size=:size and color=:color";

            Query query = session.createQuery(dbQuery);
            query.setParameter("imageName", leash.getImageName());
            query.setParameter("text", leash.getText());
            query.setParameter("size", leash.getSize());
            query.setParameter("color", leash.getColor());
            List<Leash> dbResults = query.list();

            transaction.commit();
            return !dbResults.isEmpty();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

}
