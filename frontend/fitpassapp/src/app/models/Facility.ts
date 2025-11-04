import { Discipline } from "./Discipline";
import { Exercise } from "./Exercise";
import { Image } from "./Image";
import { Manages } from "./Manages";
import { Review } from "./Review";
import { WorkDay } from "./Workday";


export class Facility {
    
    id?: number;
    description: string;
    facilityName: string;
    address: string;
    createdAt: Date;
    totalRating: number;
    city: string;
    disciplines: Discipline[];
    active:boolean;
    images: Image[];
    workDays: WorkDay[];
    exercises: Exercise[];
    reviews: Review[];
    manages: Manages[];

    constructor(

        id?:number,
        description: string = "",
        facilityName: string = "",
        address: string = "",
        createdAt: Date = new Date(),
        totalRating: number = 0.0,
        city: string = "",
        workDays: WorkDay[] = [],
        disciplines: Discipline[] = [],
        reviews: Review[] = [],
        images: Image[] = [],
        manages: Manages[] = [],
        exercises: Exercise[] = [],
        active:boolean = true) {

        this.id = id;
        this.description = description;
        this.facilityName = facilityName;
        this.address = address;
        this.createdAt = createdAt;
        this.totalRating = totalRating;
        this.city = city;
        this.workDays = workDays;
        this.disciplines = disciplines;
        this.reviews = reviews;
        this.images = images;
        this.manages = manages;
        this.exercises = exercises;
        this.active = active;
    }

}