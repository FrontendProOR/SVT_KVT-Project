export class Image{
    
    id?: number;
    path: string;

    constructor(path: string,id?:number) {
        this.id = id;
        this.path = path;
    }

}