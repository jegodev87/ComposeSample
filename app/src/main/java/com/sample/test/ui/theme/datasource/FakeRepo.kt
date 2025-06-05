package com.sample.test.ui.theme.datasource

import com.sample.test.models.Profile

object FakeRepo {

   private val names = listOf(
        "Alice Smith", "Bob Johnson", "Charlie Davis", "Diana Harris", "Ethan Walker",
        "Fiona Lewis", "George Young", "Hannah Clark", "Ian Hall", "Julia Allen",
        "Kevin Wright", "Laura King", "Michael Scott", "Nina Adams", "Oscar Baker",
        "Paula Mitchell", "Quinn Perez", "Rachel Roberts", "Steve Turner", "Tina Collins",
        "Uma Foster", "Victor Gray", "Wendy Hughes", "Xander Brooks", "Yara Morgan",
        "Zane Reed", "Amy Jenkins", "Brian Russell", "Clara Diaz", "Derek Hayes",
        "Eliza Torres", "Franklin Simmons", "Grace Patterson", "Harvey Powell", "Isla Howard",
        "Jackie Ward", "Kurt Cox", "Lana Butler", "Mason Flores", "Nora Sanders",
        "Owen Price", "Penny Bennett", "Quincy Coleman", "Rita Barnes", "Sam Morris",
        "Tara Freeman", "Ulysses Hart", "Vera Chapman", "Willie Lane", "Xenia Bishop"
    )

   private val titles = listOf(
        "Android Developer", "UI/UX Designer", "Product Manager", "QA Engineer", "DevOps Specialist",
        "iOS Developer", "Backend Engineer", "Frontend Developer", "Scrum Master", "Data Scientist"
    )

  private  val sampleProfiles = List(100) { index ->
        val name = names[index % names.size]
        val title = titles[index % titles.size]
        val imageUrl = "https://picsum.photos/seed/picsum/200/300"

        Profile(name = name, job = title, lastSeen = System.currentTimeMillis().toString(), profilePic = imageUrl)
    }

    fun getProfiles() = sampleProfiles
}