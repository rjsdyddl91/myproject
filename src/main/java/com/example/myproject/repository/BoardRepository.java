package com.example.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myproject.entity.Board;

// persistence Layer(영속 계층) : DB 연결 역할을 수행하는 계층
@Repository // @Repository : 저장소 역할의 컴포넌트 선언
public interface BoardRepository extends JpaRepository<Board, Long>{
    // JPA의 Repository : 
    // JPA의 클래스를 상속받으면 쿼리 역할을 수행하는 메소드를 사용 가능

    // JpaRepository<Entity, Id> : JPA에서 제공하는 CRUD 메소드 사용
    // Entity : 사용할 쿼리에 적용될 테이블(Entity 클래스) 작성
    // Id : 테이블(Entity)의 PK에 해당하는 변수 타입 작성
    
}
