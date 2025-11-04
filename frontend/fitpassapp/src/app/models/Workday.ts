export class WorkDay{

    id?: number;
    day: string;
    validFrom: Date;
    untilTime: string;
    fromTime: string;

    constructor(validFrom: Date, day: string, fromTime: string, untilTime: string,id?:number) {
        this.id = id;
        this.day = day;
        this.validFrom = validFrom;
        this.untilTime = untilTime;
        this.fromTime = fromTime;
    }

}