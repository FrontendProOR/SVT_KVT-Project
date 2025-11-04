export class Rate{
    
    id?:number;
    hygene:number;
    equipment:number;
    staff:number;
    space:number;

    constructor(equipment:number,
        staff:number,
        hygene:number,
        space:number,id?:number){
            this.id = id;
            this.hygene = hygene;
            this.equipment=equipment;
            this.staff = staff;
            this.space = space;
        }

}