package roomescape.core.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import roomescape.core.domain.Payment;
import roomescape.core.dto.auth.PaymentAuthorizationResponse;
import roomescape.core.dto.payment.PaymentRequest;
import roomescape.core.repository.PaymentRepository;
import roomescape.infrastructure.PaymentAuthorizationProvider;

@Service
public class PaymentService {

    private final PaymentAuthorizationProvider paymentAuthorizationProvider;
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentAuthorizationProvider paymentAuthorizationProvider,
                          PaymentRepository paymentRepository) {
        this.paymentAuthorizationProvider = paymentAuthorizationProvider;
        this.paymentRepository = paymentRepository;
    }

    public PaymentAuthorizationResponse createPaymentAuthorization() {
        return new PaymentAuthorizationResponse(paymentAuthorizationProvider.getAuthorization());
    }

    @Transactional
    public Payment save(final PaymentRequest paymentRequest) {
        Payment payment = new Payment(
                paymentRequest.getPaymentKey(), paymentRequest.getAmount(), paymentRequest.getOrderId());

        return paymentRepository.save(payment);
    }
}
