import { User } from "./User";

export class Comment{
    
    text:string;
    createdAt?:Date;
    user:User;
    comment?:Comment;
    id?:number;
    
    constructor(
        text:string,
        user:User,
        createdAt?:Date,
        comment?:Comment,
        id?:number
        
    ){
            this.text = text;
            this.createdAt = createdAt;
            this.user = user;
            this.comment = comment;
            this.id = id;
        }

}