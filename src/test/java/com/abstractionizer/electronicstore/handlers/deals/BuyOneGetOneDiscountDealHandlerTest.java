package com.abstractionizer.electronicstore.handlers.deals;

import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.model.deal.CreateOneGetOneDiscountPolicy;
import com.abstractionizer.electronicstore.model.product.ProductVo;
import com.abstractionizer.electronicstore.model.receipt.ReceiptDto;
import com.abstractionizer.electronicstore.service.DealService;
import com.abstractionizer.electronicstore.service.ProductService;
import com.abstractionizer.electronicstore.storage.rdbms.entities.DealEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyOneGetOneDiscountDealHandlerTest {

    @Spy
    ObjectMapper objectMapper;

    @Mock
    DealService dealService;

    @Mock
    ProductService productService;

    @Mock
    BuyOneGetOneDiscountDealHandler handler;

    @BeforeEach
    public void init() {
        handler = new BuyOneGetOneDiscountDealHandler(
                objectMapper, dealService, productService
        );
    }

    @Test
    public void testCreateDeal_WhenNameIsNull_ThenThrowBusinessException() throws IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Name attribute must not be null or empty");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenApplyOrderIsNull_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Apply order attribute must not be null and must be greater than 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenApplyOrderIsLessThanOne_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(0);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Apply order attribute must not be null and must be greater than 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenStackableIsNull_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(0);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Stackable must not be null");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenProductIdIsNull_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "CreateOneGetOneDiscountDto productId attribute must not be null or value must be greater than 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenProductIdIsLessThan1_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(0);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "CreateOneGetOneDiscountDto productId attribute must not be null or value must be greater than 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenDiscountIsNull_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "CreateOneGetOneDiscountDto discount attribute must not be null and should be greater than 0 and less and 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenDiscountIsLessAndEqualToZero_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(BigDecimal.ZERO);

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "CreateOneGetOneDiscountDto discount attribute must not be null and should be greater than 0 and less and 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenDiscountEqualOrGreaterThanOne_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(BigDecimal.ONE);

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "CreateOneGetOneDiscountDto discount attribute must not be null and should be greater than 0 and less and 1");

        verify(productService, never()).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenProductDoesNotExist_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);
        doThrow(BusinessException.class)
                .when(productService)
                        .ifProductNotExistsThenThrow(anyInt());

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Product id '1' was not found");

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, never()).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenDealNameExists_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);
        doThrow(BusinessException.class)
                .when(dealService)
                .ifDealNameExistThenThrow(anyString());

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Deal name 'name' already exists");

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, times(1)).ifDealNameExistThenThrow(anyString());
        verify(dealService, never()).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenApplyOrderExist_ThenThrowBusinessException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);
        doThrow(BusinessException.class)
                .when(dealService)
                .ifApplyOrderExistThenThrow(anyInt());

        assertThrows(BusinessException.class,
                () -> handler.createDeal(request),
                "Apply order '1' already exists");

        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, times(1)).ifDealNameExistThenThrow(anyString());
        verify(dealService, times(1)).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, never()).insert(any(DealEntity.class));
    }

    @Test
    public void testCreateDeal_WhenValidationPassed_ThenAllMethodsAreExecuted() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);


        handler.createDeal(request);


        verify(productService, times(1)).ifProductNotExistsThenThrow(anyInt());
        verify(dealService, times(1)).ifDealNameExistThenThrow(anyString());
        verify(dealService, times(1)).ifApplyOrderExistThenThrow(anyInt());
        verify(dealService, times(1)).insert(any(DealEntity.class));
    }

    @Test
    public void testApply_WhenProductDoesNotExist_ThenReturnOriginalReceiptDto(){

        ProductVo productVo = getProductVo(2, 1, new BigDecimal("100"));

        Map<Integer, ProductVo> basket = new HashMap<>();
        basket.put(2, productVo);

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setBasket(basket);
        receiptDto.setDealsApplied(new ArrayList<>());
        receiptDto.setTotalPrice(BigDecimal.ZERO);

        String policyStr = "{\"productId\":1,\"discount\":0.5}";

        ReceiptDto result = handler.apply(receiptDto, "deal", policyStr);

        Map<Integer,ProductVo> basketResult = result.getBasket();
        assertNull(basketResult.get(1));
    }

    @Test
    public void testApply_WhenProductExistsButQuantityIsOne_ThenReturnOriginalReceiptDto(){

        ProductVo productVo = getProductVo(1, 1, new BigDecimal("10000"));

        Map<Integer, ProductVo> basket = new HashMap<>();
        basket.put(1, productVo);

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setBasket(basket);
        receiptDto.setDealsApplied(new ArrayList<>());
        receiptDto.setTotalPrice(BigDecimal.ZERO);

        String policyStr = "{\"productId\":1,\"discount\":0.5}";

        ReceiptDto result = handler.apply(receiptDto, "deal", policyStr);

        Map<Integer,ProductVo> basketResult = result.getBasket();
        ProductVo productResult = basketResult.get(1);

        assertEquals(result.getDealsApplied().size(), 0);
        assertEquals(productResult.getSubTotal(), new BigDecimal("10000"));
    }

    @Test
    public void testApply_WhenProductExistsAndQuantityIsTwo_ThenReturnOriginalReceiptDto(){

        ProductVo productVo = getProductVo(1, 2, new BigDecimal("20000"));

        Map<Integer, ProductVo> basket = new HashMap<>();
        basket.put(1, productVo);

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setBasket(basket);
        receiptDto.setDealsApplied(new ArrayList<>());
        receiptDto.setTotalPrice(BigDecimal.ZERO);

        String policyStr = "{\"productId\":1,\"discount\":0.5}";

        ReceiptDto result = handler.apply(receiptDto, "deal", policyStr);

        Map<Integer,ProductVo> basketResult = result.getBasket();
        ProductVo productResult = basketResult.get(1);

        assertEquals(productResult.getSubTotal(), new BigDecimal("15000.0"));
    }

    @Test
    public void testApply_WhenProductExistsAndQuantityIsThree_ThenReturnOriginalReceiptDto(){

        ProductVo productVo = getProductVo(1, 3, new BigDecimal("30000"));

        Map<Integer, ProductVo> basket = new HashMap<>();
        basket.put(1, productVo);

        ReceiptDto receiptDto = new ReceiptDto();
        receiptDto.setBasket(basket);
        receiptDto.setDealsApplied(new ArrayList<>());
        receiptDto.setTotalPrice(BigDecimal.ZERO);

        String policyStr = "{\"productId\":1,\"discount\":0.5}";

        ReceiptDto result = handler.apply(receiptDto, "deal", policyStr);

        Map<Integer,ProductVo> basketResult = result.getBasket();
        ProductVo productResult = basketResult.get(1);

        assertEquals(productResult.getSubTotal(), new BigDecimal("25000.0"));
    }


    private ProductVo getProductVo(Integer productId, Integer quantity, BigDecimal subTotal){
        return ProductVo.builder()
                .productId(productId)
                .quantity(quantity)
                .unitPrice(new BigDecimal("10000"))
                .subTotal(subTotal)
                .build();
    }

    public static void main(String[] args) throws IOException {

        HttpServletRequest request = mock(HttpServletRequest.class);

        CreateOneGetOneDiscountPolicy policy = new CreateOneGetOneDiscountPolicy();
        policy.setName("name");
        policy.setApplyOrder(1);
        policy.setStackable(true);
        policy.setProductId(1);
        policy.setDiscount(new BigDecimal("0.5"));

        String json = new ObjectMapper().writeValueAsString(policy);
        ServletInputStream inputStream = new DelegatingServletInputStream(
                new ByteArrayInputStream(json.getBytes())
        );
        when(request.getInputStream()).thenReturn(inputStream);
    }


}