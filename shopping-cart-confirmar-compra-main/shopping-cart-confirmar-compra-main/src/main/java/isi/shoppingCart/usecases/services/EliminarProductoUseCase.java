package isi.shoppingCart.usecases.services;

import isi.shoppingCart.entities.Cart;
import isi.shoppingCart.entities.Product;
import isi.shoppingCart.usecases.dto.OperationResult;
import isi.shoppingCart.usecases.ports.CartRepository;
import isi.shoppingCart.usecases.ports.ProductRepository;

public class EliminarProductoUseCase {

    private ProductRepository productRepository;
    private CartRepository cartRepository;


    public EliminarProductoUseCase(ProductRepository productRepository,
                                           CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public OperationResult execute(int productId) {
        Product product = productRepository.findById(productId);

        if (product == null) {
            return OperationResult.fail("Producto no encontrado.");
        }

        Cart cart = cartRepository.getCart();
        boolean removed = cart.removeProduct(product);

        if (!removed) {
            return OperationResult.fail("El producto no está en el carrito.");
        }

        cartRepository.save(cart);

        return OperationResult.ok("Producto eliminado del carrito exitosamente");
    }
}
