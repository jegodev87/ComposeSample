package com.sample.test

import com.sample.test.models.Student
import com.sample.test.domain.model.TaskModel

object Util {

    fun generateRandomNames(count: Int): List<String> {

        val firstSetOfNames = listOf("Jeev", "Rahul", "Vineeth", "Akash", "Arun", "Priya", "Remya", "Aki")
        val secondNames = listOf("x", "y", "z", "b","k", "d")



        return List(count){
            val first  = firstSetOfNames.random()
            val second = secondNames.random()
            "$first $second"
        }
    }

    fun generateRandomStudents(count: Int): List<Student> {
        val firstSetOfNames = listOf("Jeev", "Rahul", "Vineeth", "Akash", "Arun", "Priya", "Remya", "Aki")
        val areaList = listOf(
            "Bangalore", "Chennai", "Hyderabad", "Mumbai", "Delhi", "Kochi",
            "Pune", "Trivandrum", "Ahmedabad", "Kolkata", "Coimbatore", "Mysore"
        )

        return List(count) {
            val name = firstSetOfNames.random()
            val area = areaList.random()
            Student(name = name, area = area)
        }
    }

    fun generateMockTasks(): List<TaskModel> {
        val tasks = mutableListOf<TaskModel>()
        for (i in 1..100) {
            tasks.add(
                TaskModel(
                    title = "Task $i",
                    description = "This is the description for task $i.",
                    isCompleted = i % 2 == 0 // Alternate between true and false
                )
            )
        }
        return tasks
    }

}