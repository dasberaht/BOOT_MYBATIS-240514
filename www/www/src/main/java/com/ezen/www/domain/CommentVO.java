package com.ezen.www.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

//    comment table 생성
//    create table comment(
//            cno bigint auto_increment,
//            bno bigint not null,
//            writer varchar(200) not null,
//    content text not null,
//    reg_at datetime default now(),
//    mod_at datetime default now(),
//    primary key(cno));

    private long cno;
    private long bno;
    private String writer;
    private String content;
    private String regAt;
    private String modAt;


}
