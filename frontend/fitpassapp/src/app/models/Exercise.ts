export class Exercise {
    
    id: number | null;
    untilDate: Date;
    fromDate: Date;
    userId: number;
    facilityId: number;

    constructor(fromDate: Date, untilDate: Date, facilityId: number, userId: number,id?: number | null) {
        this.id = id ?? null;
        this.untilDate = untilDate;
        this.fromDate = fromDate;
        this.userId = userId;
        this.facilityId = facilityId;
    }

}
