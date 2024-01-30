package com.abstractionizer.electronicstore.factories;

import com.abstractionizer.electronicstore.enumerations.DealType;
import com.abstractionizer.electronicstore.exceptions.BusinessException;
import com.abstractionizer.electronicstore.handlers.deals.AbstractDealHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.abstractionizer.electronicstore.errors.Error.DATA_NOT_FOUND;

@Component
public class DealFactory {

    private final Map<DealType, AbstractDealHandler<?>> dealHandlerMap;

    public DealFactory(List<AbstractDealHandler<?>> abstractDealHandlers) {
        this.dealHandlerMap = abstractDealHandlers.stream().collect(Collectors.toMap(AbstractDealHandler::getDealType, v -> v));
    }

    public void createDeal(@NonNull final DealType type, @NonNull final HttpServletRequest request){

        AbstractDealHandler<?> abstractDealHandler = Optional.ofNullable(dealHandlerMap.get(type))
                .orElseThrow(() -> new BusinessException(DATA_NOT_FOUND, String.format("Deal handler '%s' has not been implemented", type)));

        abstractDealHandler.createDeal(request);
    }
}
