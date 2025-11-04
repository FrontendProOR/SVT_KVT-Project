export class Discipline{
    
    id?:number;
    name:String;

    constructor(disciplineName:String,id?:number){
        this.id = id;
        this.name = disciplineName;
    }
    
}