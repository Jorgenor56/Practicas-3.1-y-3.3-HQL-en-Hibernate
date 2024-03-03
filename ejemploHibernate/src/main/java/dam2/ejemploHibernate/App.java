package dam2.ejemploHibernate;

public class App {
    public static void main(String[] args) {
        HibernateEmpresa gestor = new HibernateEmpresa();

        Productos nuevoProducto = new Productos();
        nuevoProducto.setNombre("NombreProducto");
        nuevoProducto.setPrecio(123.45);
        gestor.insertarProducto(nuevoProducto);

        System.out.println("Lista de productos:");
        for (Productos producto : gestor.obtenerProductos()) {
            System.out.println(producto);
        }

        nuevoProducto.setId(1);
        nuevoProducto.setPrecio(150.00);
        gestor.actualizarProducto(nuevoProducto);

        gestor.eliminarProducto(1);
    }
}