# Use an official Node.js image as a parent image
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the package.json and package-lock.json files
COPY package*.json ./

# Install Angular dependencies
RUN npm install -g @angular/cli

# Install app dependencies
RUN npm install

# Copy the Angular app's source code into the container
COPY . .

# Copy the images from the images folder (shared by frontend and backend)
# COPY ../../images /app/assets/images

# Build the Angular app
RUN npm run build --prod

# Expose the port the app runs on
EXPOSE 4200

# Start the Angular app
CMD ["npm", "start"]
