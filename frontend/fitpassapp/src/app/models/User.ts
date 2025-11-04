import { Image } from "./Image";

export class User {
    
    id?:number;
    email: String | null;
    address: String | null;
    password: String | null;
    createdAt: Date;
    image:Image | null;
    surname: String | null;
    name: String | null;
    birthDate: Date | string | null;
    phoneNumber: String | null;
    zipCode: String | null;
    city: String | null;

    public constructor(email: String | null = null, password: String | null = null, address: String | null = null, name: String | null = null, surname: String | null = null, phoneNumber: String | null = null, birthDate: Date | null = null, city: String | null = null, zipCode: String | null = null, image:Image | null = null, id?:number) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.password = password;
        this.createdAt = new Date();
        this.image = image;
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

}
