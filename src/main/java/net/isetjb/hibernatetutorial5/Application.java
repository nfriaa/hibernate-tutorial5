package net.isetjb.hibernatetutorial5;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Application class.
 *
 * @author Nafaa Friaa (nafaa.friaa@isetjb.rnu.tn)
 */
public class Application
{
    /**
     * Attribute declaration for factory to share between methods.
     */
    private static SessionFactory factory;

    public static void main(String[] args)
    {
        System.out.println("JavaSE + Maven + Hibernate + MySQL : Many to One Association");

        // Open connection  pool
        factory = HibernateUtil.getSessionFactory();

        Session session = factory.openSession();
        Transaction transaction = null;

        try
        {
            transaction = session.beginTransaction();

            // new product
            Product product_x = new Product();
            product_x.setName("Prod x");
            product_x.setPrice(456);
            session.save(product_x);

            // new product
            Product product_y = new Product();
            product_y.setName("Prod y");
            product_y.setPrice(123);
            session.save(product_y);

            // new product
            Product product_z = new Product();
            product_z.setName("Prod z");
            product_z.setPrice(789);
            session.save(product_z);

            // new category
            Category category_a = new Category();
            category_a.setName("Cat a");
            category_a.getProducts().add(product_x);
            category_a.getProducts().add(product_y);
            session.save(category_a);

            // new category
            Category category_b = new Category();
            category_b.setName("Cat b");
            category_b.getProducts().add(product_z);
            session.save(category_b);

            transaction.commit();
        } catch (Exception e)
        {
            if (transaction != null)
            {
                transaction.rollback();
            }
            //e.printStackTrace();
            System.out.println("ERROR: " + e.getMessage());
        } finally
        {
            session.close();
        }

        // Cleaning up connection pool
        factory.close();
    }

}
