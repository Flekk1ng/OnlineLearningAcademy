create table if not exists users
(
    id int NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    first_name varchar(255) NOT NULL,
    role varchar(255) NOT NULL DEFAULT 'STUDENT',
    PRIMARY KEY (id)
);

create table if not exists areas_of_study
(
	id int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    description varchar(1000) NOT NULL,
    PRIMARY KEY (id)
);

create table if not exists courses 
(
	id int NOT NULL AUTO_INCREMENT,
	area_of_study_id int NOT NULL,
	name varchar(255) NOT NULL UNIQUE,
    description varchar(1000) NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (area_of_study_id) REFERENCES areas_of_study (id)
);

create table if not exists lessons 
(
  	id int NOT NULL AUTO_INCREMENT,
	course_id int NOT NULL,
	topic varchar(255) NOT NULL,
    content varchar(255) NOT NULL,
    date datetime NOT NULL,
    status varchar(20) NOT NULL, 
    PRIMARY KEY (id),
	FOREIGN KEY (course_id) REFERENCES courses (id)
);

create table if not exists teacher_courses 
(
	id int NOT NULL AUTO_INCREMENT,
    teacher_id int NOT NULL,
    course_id int NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (teacher_id) REFERENCES users (id),
	FOREIGN KEY (course_id) REFERENCES courses (id)
);

create table if not exists student_courses 
(
	id int NOT NULL AUTO_INCREMENT,
    student_id int NOT NULL,
    course_id int NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (student_id) REFERENCES users (id),
	FOREIGN KEY (course_id) REFERENCES courses (id)
);

create table if not exists enroll_lessons
(
	id int NOT NULL AUTO_INCREMENT,
	student_id int NOT NULL,
    lesson_id int NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (student_id) REFERENCES users (id),
	FOREIGN KEY (lesson_id) REFERENCES lessons (id)
);

INSERT INTO users(email, password, last_name, first_name, role)
	VALUES ('admin@mail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Admin', 'Admin', 'ADMIN'),
		('teacher1@mail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Smith', 'Olivia', 'TEACHER'),
		('teacher2@mail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Taylor', 'Noah', 'TEACHER'),
		('teacher3@mail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Anderson', 'Isabella', 'TEACHER'),
		('teacher4@mail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Jackson', 'Michael', 'TEACHER'),
		('student1@gmail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Johnson', 'Emma', 'STUDENT'),
		('student2@gmail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Brown', 'Ethan', 'STUDENT'),
		('student3@gmail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Davis', 'Ava', 'STUDENT'),
		('student4@gmail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Garcia', 'William', 'STUDENT'),
		('student5@gmail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Rodriguez', 'Sophia', 'STUDENT'),
		('student6@gmail.com', '$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm', 'Wilson', 'James','STUDENT'),
		('student7@gmail.com','$2a$12$N9oy4dYcWuxonOQAO54xkuefPptogK1sZkdjRRMdLxcw51r4446Pm','Martinez','Charlotte','STUDENT');

       
INSERT INTO areas_of_study(name, description)
	VALUES ('Web Development', 'Learn the skills to build and design websites and web applications.'),
		('Data Science', 'Discover the tools and techniques to extract insights from large datasets.'),
		('Mobile Development', 'Create mobile applications for iOS and Android devices.'),
		('Cybersecurity', 'Protect systems and networks from digital attacks.'),
		('Language Learning', 'Improve your language skills in English and German.');

INSERT INTO courses(area_of_study_id, name, description)
	VALUES (1, 'HTML & CSS', 'Learn the basics of web design with HTML and CSS.'),
		(1, 'JavaScript', 'Add interactivity to your websites with JavaScript.'),
		(1, 'React', 'Build modern web applications with the React library.'),
		(2, 'Python for Data Science', 'Use Python to analyze and visualize data.'),
		(2, 'Machine Learning', 'Explore the fundamentals of machine learning algorithms.'),
		(3, 'iOS Development with Swift', 'Create native iOS applications using the Swift programming language.'),
		(3, 'Android Development with Kotlin', 'Build Android applications using the Kotlin programming language.'),
		(4, 'Network Security', 'Protect networks from unauthorized access and attacks.'),
		(4, 'Ethical Hacking', 'Learn the techniques used by hackers to identify and exploit vulnerabilities.'),
		(5, 'English for IT Professionals', 'Improve your English language skills in an IT context.'),
		(5, 'German for IT Professionals', 'Learn German language skills relevant to the IT industry.');
  
INSERT INTO lessons(course_id, topic, content, date, status)
	VALUES (1, 'Introduction to HTML', 'Learn the basics of HTML and how to structure a web page.', '2022-01-01', 'HAS ENDED'),
		(1, 'Styling with CSS', 'Discover how to add style to your web pages using CSS.', '2022-01-08', 'HAS ENDED'),
		(1, 'CSS Layouts', 'Explore different layout techniques using CSS.', '2022-01-15', 'HAS ENDED'),
		(2, 'JavaScript Basics', 'Learn the fundamentals of the JavaScript programming language.', '2022-01-01', 'HAS ENDED'),
		(2, 'DOM Manipulation', 'Discover how to manipulate the Document Object Model (DOM) using JavaScript.', NOW(), 'IN PROGRESS'),
		(2, 'Events and Callbacks', 'Learn how to handle events and use callback functions in JavaScript.', '2022-02-01', 'IS EXPECTED'),
		(3, 'React Fundamentals', 'Explore the basics of building web applications using React.', NOW(), 'IN PROGRESS'),
		(3, 'React Components', 'Learn how to create and use components in React.', '2022-02-08', 'IS EXPECTED'),
		(3, 'React State Management', 'Discover how to manage state in a React application.', '2022-02-15', 'IS EXPECTED'),
		(4, 'Python Basics', 'Learn the fundamentals of the Python programming language.', NOW(), 'IN PROGRESS'),
		(4, 'Data Analysis with Pandas', 'Explore how to analyze data using the Pandas library.', NOW(), 'IN PROGRESS'),
		(4, 'Data Visualization with Matplotlib', 'Learn how to create visualizations of data using Matplotlib.', NOW(), 'IN PROGRESS'),
		(5, 'Introduction to Machine Learning', 'Learn the basics of machine learning and how it can be used to make predictions.', '2022-01-01', 'HAS ENDED'),
		(5, 'Supervised Learning', 'Explore the concepts of supervised learning and how to train models using labeled data.', '2022-01-08', 'HAS ENDED'),
		(5, 'Unsupervised Learning', 'Discover the techniques of unsupervised learning and how to find patterns in data without labels.', '2022-01-15', 'HAS ENDED'),
		(6, 'Swift Basics', 'Learn the fundamentals of the Swift programming language.', '2023-10-20', 'IS EXPECTED'),
		(6, 'Building iOS Interfaces', 'Discover how to create user interfaces for iOS applications using SwiftUI.', '2023-10-20', 'IS EXPECTED'),
		(6, 'iOS App Architecture', 'Explore the principles of good app architecture and how to structure your code for maintainability.', NOW(), 'IN PROGRESS'),
		(7, 'Kotlin Basics', 'Learn the fundamentals of the Kotlin programming language.', '2023-10-20', 'IS EXPECTED'),
		(7, 'Building Android Interfaces', 'Discover how to create user interfaces for Android applications using XML and Kotlin.', '2023-10-20', 'IS EXPECTED'),
		(7, 'Android App Architecture', 'Explore the principles of good app architecture and how to structure your code for maintainability.', NOW(), 'IN PROGRESS'),
		(8, 'Introduction to Network Security', 'Learn the basics of network security and how to protect networks from unauthorized access.', NOW(), 'IN PROGRESS'),
		(8, 'Firewalls and VPNs', 'Discover how firewalls and VPNs can be used to secure network traffic.', '2023-10-20', 'IS EXPECTED'),
		(8, 'Intrusion Detection and Prevention', 'Explore the techniques used to detect and prevent unauthorized access to networks.', '2023-10-20', 'IS EXPECTED'),
		(9, 'Introduction to Ethical Hacking', 'Learn the basics of ethical hacking and how it can be used to identify vulnerabilities in systems.', '2023-10-20', 'IS EXPECTED'),
		(9, 'Penetration Testing', 'Discover the techniques used by penetration testers to simulate attacks on systems.', '2023-10-20', 'IS EXPECTED'),
		(9, 'Social Engineering', 'Explore the methods used by attackers to manipulate individuals into divulging sensitive information.', '2023-10-20', 'IS EXPECTED');
        
INSERT INTO teacher_courses(teacher_id, course_id)
	VALUES (2, 1),
		(2, 2),
		(3, 3),
		(3, 4),
		(4, 5),
		(4, 6),
		(5, 7),
		(5, 8);

INSERT INTO student_courses(student_id, course_id)
	VALUES (6, 1),
		(6, 2),
		(7, 1),
		(7, 3),
		(8, 4),
		(8, 5),
		(9, 6),
		(9, 7),
		(10, 8),
		(11, 1),
		(12, 2);

INSERT INTO enroll_lessons(student_id, lesson_id)
	VALUES (6, 1),
		(6, 2),
		(7, 1),
		(7, 3),
		(8, 4),
		(8, 5),
		(9, 6),
		(9, 7),
		(10, 8),
		(11, 1),
		(12, 2),
        (6, 3),
		(6, 4),
		(6, 5),
		(7, 6),
		(7, 7),
		(7, 8),
		(8, 9),
		(8, 10),
		(8, 11),
		(9, 12),
		(9, 13),
		(9, 14),
		(10, 15),
		(10, 16),
		(10, 17),
		(11, 18),
		(11, 19),
		(11, 20);


