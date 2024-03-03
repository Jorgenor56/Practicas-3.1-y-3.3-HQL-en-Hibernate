package dam2.ejemploHibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateEmpresa {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Falló la creación de la SessionFactory inicial." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void insertarProducto(Productos producto) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(producto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public List<Productos> obtenerProductos() {
        Session session = sessionFactory.openSession();
        List<Productos> productos = null;

        try {
            productos = session.createQuery("FROM Productos", Productos.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return productos;
    }

    public void actualizarProducto(Productos producto) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(producto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void eliminarProducto(int productoId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Productos producto = session.find(Productos.class, productoId);
            if (producto != null) {
                session.remove(producto);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public List<Clientes> obtenerClientes() {
        Session session = sessionFactory.openSession();
        List<Clientes> clientes = null;

        try {
            clientes = session.createQuery("FROM Clientes", Clientes.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return clientes;
    }

    public void insertarCliente(Clientes cliente) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void eliminarCliente(int clienteId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Clientes cliente = session.find(Clientes.class, clienteId);
            if (cliente != null) {
                session.remove(cliente);
                System.out.println("Cliente eliminado con éxito.");
            } else {
                System.out.println("Cliente no encontrado con ID: " + clienteId);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void actualizarCliente(Clientes cliente) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.merge(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void eliminarClientePorNombre(String nombreCliente) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Clientes WHERE nombre = :nombre");
            query.setParameter("nombre", nombreCliente);
            int result = query.executeUpdate();
            if (result > 0) {
                System.out.println("Cliente(s) eliminado(s) con éxito.");
            } else {
                System.out.println("No se encontraron clientes con el nombre: " + nombreCliente);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Clientes> obtenerClientesPorPais(String paisCliente) {
        Session session = sessionFactory.openSession();
        List<Clientes> clientes = null;

        try {
            clientes = session.createQuery("FROM Clientes WHERE pais = :pais", Clientes.class)
                              .setParameter("pais", paisCliente)
                              .list();
            System.out.println("Número de clientes en " + paisCliente + ": " + clientes.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return clientes;
    }

    public String buscarPaisDeCliente(String nombreCliente) {
        Session session = sessionFactory.openSession();
        String pais = null;

        try {
            Clientes cliente = (Clientes) session.createQuery("FROM Clientes WHERE nombre = :nombre")
                                                 .setParameter("nombre", nombreCliente)
                                                 .uniqueResult();
            if (cliente != null) {
                pais = cliente.getPais();
                System.out.println("El país del cliente " + nombreCliente + " es: " + pais);
            } else {
                System.out.println("Cliente no encontrado con el nombre: " + nombreCliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return pais;
    }

}