DROP TABLE IF EXISTS user;

CREATE TABLE user(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL,
    created_at CURRENT_DATE NOT NULL
)

DROP TABLE IF EXISTS post

CREATE TABLE  post (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    content VARCHAR(250) NOT NULL,
    created_at CURRENT_DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
)

DROP TABLE IF EXISTS users_follower

CREATE TABLE  users_follower (
    follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    FOREIGN KEY (follower_id) REFERENCES user(id),
    FOREIGN KEY (followee_id) REFERENCES user(id)
)

