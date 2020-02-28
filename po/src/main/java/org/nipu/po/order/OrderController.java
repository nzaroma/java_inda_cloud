package org.nipu.po.order;

import org.nipu.po.order.clients.ProductSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO: This is autogenerated Java-Doc.
 *
 * @author Nikita_Puzankov
 */
@RestController
@EnableBinding(Source.class)
public class OrderController {

    @Autowired
    private Source source;

    private final ProductOrderRepository orderRepository;
    private final ProductSpecificationRepository specificationRepository;

    public OrderController(ProductOrderRepository orderRepository, ProductSpecificationRepository specificationRepository
//            , Source source
    ) {
        this.orderRepository = orderRepository;
        this.specificationRepository = specificationRepository;
//        this.source = source;
    }

    @PutMapping("/catalog/{specificationId}/order")
    public ProductOrder orderProductBySpecificationId(@PathVariable String specificationId) {
        if (specificationRepository.existsById(specificationId) == null) {
            throw new RuntimeException("There is no product specification with Id: " + specificationId);
        }
        ProductOrder productOrder = orderRepository.save(new ProductOrder(null, specificationId, 1l));
        source.output().send(MessageBuilder.withPayload("testMessage").build());
        return productOrder;
    }

    @GetMapping("/catalog/{specificationId}/orders")
    public List<ProductOrder> getOrdersForCatalog(@PathVariable String specificationId) {
        if (specificationRepository.existsById(specificationId) == null) {
            throw new RuntimeException("There is no product specification with Id: " + specificationId);
        }
        source.output().send(MessageBuilder.withPayload(orderRepository.findAll()).build());
        return orderRepository.findAll();
    }
}
