package io.github.anjoismysign.antsimulator.asset;

import io.github.anjoismysign.holoworld.asset.DataAsset;
import io.github.anjoismysign.holoworld.asset.IdentityGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public record KillPayment(String identifier,
                          String currency,
                          double minimumAmount,
                          double maximumAmount,
                          boolean roundNearest,
                          double legendaryMultiplier) implements DataAsset {

    /**
     * Gets a random amount using the inclusive minimumAmount and inclusive maximumAmount
     * @return The random amount
     */
    public double getRandomAmount(boolean isLegendary) {
        double randomAmount = ThreadLocalRandom.current()
                .nextDouble(minimumAmount, maximumAmount);
        if (isLegendary){
            randomAmount = randomAmount * legendaryMultiplier;
        }
        return roundNearest ? Math.round(randomAmount) : randomAmount;
    }

    public static final class Info implements IdentityGenerator<KillPayment> {

        private String currency;
        private double minimumAmount;
        private double maximumAmount;
        private boolean roundNearest;
        private double legendaryMultiplier;

        @Override
        public @NotNull KillPayment generate(@NotNull String identifier) {
            return new KillPayment(identifier, currency, minimumAmount, maximumAmount, roundNearest, legendaryMultiplier);
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getMinimumAmount() {
            return minimumAmount;
        }

        public void setMinimumAmount(double minimumAmount) {
            this.minimumAmount = minimumAmount;
        }

        public double getMaximumAmount() {
            return maximumAmount;
        }

        public void setMaximumAmount(double maximumAmount) {
            this.maximumAmount = maximumAmount;
        }

        public boolean isRoundNearest() {
            return roundNearest;
        }

        public void setRoundNearest(boolean roundNearest) {
            this.roundNearest = roundNearest;
        }

        public double isLegendaryMultiplier() {
            return legendaryMultiplier;
        }

        public void setLegendaryMultiplier(double legendaryMultiplier) {
            this.legendaryMultiplier = legendaryMultiplier;
        }
    }
}
