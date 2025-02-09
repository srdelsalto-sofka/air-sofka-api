package ec.com.airsofka.booking.queries.usecases;

import ec.com.airsofka.booking.queries.query.GetAmountsQuery;
import ec.com.airsofka.booking.queries.responses.CostBreakdownResponse;
import ec.com.airsofka.generics.interfaces.IUseCaseGet;
import ec.com.airsofka.generics.utils.QueryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public class GetCostBreakdownUseCase implements IUseCaseGet<GetAmountsQuery, CostBreakdownResponse> {


    private static final BigDecimal AIRPORT_TAX = BigDecimal.valueOf(10.00);
    private static final BigDecimal BOOKING_FEE = BigDecimal.valueOf(15.00);
    private static final BigDecimal FUEL_INSURANCE = BigDecimal.valueOf(18.00);
    private static final BigDecimal ADDITIONAL_CHARGES = BigDecimal.valueOf(25.00);


    public Mono<BigDecimal> getSeatPriceById(String seatId) {

        return Mono.just(BigDecimal.valueOf(759.00));
    }

    @Override
    public Mono<QueryResponse<CostBreakdownResponse>> get(GetAmountsQuery request) {
        return Flux.fromIterable(request.getPassengers())
                .flatMap(passenger -> getSeatPriceById(passenger.getSeatId()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .map(seatTotal -> {
                    BigDecimal discountAmount = BigDecimal.ZERO;
                    BigDecimal total = seatTotal.add(ADDITIONAL_CHARGES)
                            .add(FUEL_INSURANCE)
                            .add(AIRPORT_TAX)
                            .add(BOOKING_FEE);

                    if(request.isFrequent()){
                        discountAmount = total.multiply(BigDecimal.valueOf(0.10));
                    }
                    BigDecimal finalAmount = total.subtract(discountAmount);

                    return QueryResponse.ofSingle(new CostBreakdownResponse(seatTotal,
                            AIRPORT_TAX,
                            finalAmount,
                            BOOKING_FEE,
                            FUEL_INSURANCE,
                            ADDITIONAL_CHARGES,
                            discountAmount));
                });


    }
}
