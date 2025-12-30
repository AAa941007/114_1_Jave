/** 財務計費介面 [來源 166] */
interface Billable {
    double calculateFee();
    double getInsuranceCoverage();
    default double getPatientPayable() { return calculateFee() - getInsuranceCoverage(); }
}