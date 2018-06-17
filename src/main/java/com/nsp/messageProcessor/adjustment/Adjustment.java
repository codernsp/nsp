package com.nsp.messageProcessor.adjustment;

/**
 * Class stores adjustment
 */
public class Adjustment {

    //adjustment amount
    private double adjustmentAmount;

    //type of adjustment
    private AdjustmentType adjustmentType;

    /**
     *
     * @return double adj. amount
     */
    public double getAdjustmentAmount() {
        return adjustmentAmount;
    }

    /**
     *
     * @return adjustment Type
     */
    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    /**
     * sets amount
     * @param adjustmentAmount
     */
    public void setAdjustmentAmount(double adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    /**
     * sets type
     * @param adjustmentType
     */
    public void setAdjustmentType(AdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @Override
    public String toString() {
        return "Adjustment{" +
                "adjustmentAmount=" + adjustmentAmount +
                ", adjustmentType=" + adjustmentType +
                '}';
    }
}
