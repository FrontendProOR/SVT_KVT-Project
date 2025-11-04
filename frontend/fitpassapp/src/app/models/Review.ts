import { Facility } from "./Facility";
import { Rate } from "./Rate";
import { User } from "./User";
import { Comment } from "./Comment";

export class Review {

    id?: number;
    exerciseCount:number;
    createdAt?: Date;
    isHidden:boolean;
    comment?: Comment;
    user: User;
    facilityId:number;
    rate: Rate;
    active:boolean;

    constructor(exerciseCount:number,isHidden:boolean = false, user: User, rate: Rate,facilityId:number,comment?: Comment,createdAt?: Date,id?: number,active:boolean = true) {
        this.id = id;
        this.exerciseCount = exerciseCount;
        this.createdAt = createdAt;
        this.isHidden = isHidden
        this.comment = comment;
        this.user = user;
        this.facilityId = facilityId;
        this.rate = rate;
        this.active = active;
    }

}
