/** 候診佇列介面 [來源 167, 190] */
interface Queueable {
    int getPriority();
    int getEstimatedWaitTime();
}