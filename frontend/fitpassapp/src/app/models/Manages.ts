export class Manages {
    
    id?: number;
    endDate?: Date;
    startDate?: Date;
    facilityId: number;
    userId: number;

    constructor(userId: number, facilityId: number,startDate?: Date, endDate?: Date,id?: number) {
        this.id = id;
        this.endDate = endDate;
        this.startDate = startDate;
        this.facilityId = facilityId;
        this.userId = userId;
    }

}