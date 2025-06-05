package com.sample.test.ui.theme.datasource

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.test.models.MovieResponseItem

object FakeMovieRepo {

    val mockMovieResponse = """
[
  {
    "key": "popular_movies",
    "movies": [
      {
        "name": "Deadpool & Wolverine",
        "release_year": 2024,
        "description": "Deadpool and Wolverine team up to face a new threat, blending action and humor.",
        "image_url": "https://image.tmdb.org/t/p/w500/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg"
      },
      {
        "name": "Inside Out 2",
        "release_year": 2024,
        "description": "Riley's emotions embark on a new adventure as she navigates her teenage years.",
        "image_url": "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg"
      },
      {
        "name": "The Fall Guy",
        "release_year": 2024,
        "description": "A stuntman tracks down a missing actor while working on a movie set.",
        "image_url": "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg"
      },
      {
        "name": "Dune: Part Two",
        "release_year": 2024,
        "description": "Paul Atreides unites with Chani to avenge his family and take control of Arrakis.",
        "image_url": "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"
      },
      {
        "name": "Godzilla x Kong: The New Empire",
        "release_year": 2024,
        "description": "The two Titans unite to face a colossal undiscovered threat.",
        "image_url": "https://image.tmdb.org/t/p/w500/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg"
      },
      {
        "name": "Civil War",
        "release_year": 2024,
        "description": "A team of journalists travel through a dystopian America to cover its second civil war.",
        "image_url": "https://image.tmdb.org/t/p/w500/5P8SmMzSNYikXpxil6BYzJ16611.jpg"
      }
    ]
  },
  {
    "key": "recent_movies",
    "movies": [
      {
        "name": "The Seed of the Sacred Fig",
        "release_year": 2024,
        "description": "A political drama set in Iran during times of social unrest.",
        "image_url": "https://image.tmdb.org/t/p/w500/xF14D3MBkkMhAtKw5TyPk7v2sX6.jpg"
      },
      {
        "name": "The Substance",
        "release_year": 2024,
        "description": "A horror body-transformation film starring Demi Moore and Margaret Qualley.",
        "image_url": "https://image.tmdb.org/t/p/w500/vIgyYkXkg6NC2whRbYjBD7eb3Er.jpg"
      },
      {
        "name": "Kinds of Kindness",
        "release_year": 2024,
        "description": "An anthology film of three stories exploring control and submission.",
        "image_url": "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg"
      },
      {
        "name": "The Shrouds",
        "release_year": 2024,
        "description": "A tech magnate builds a machine to contact the dead in this Cronenberg thriller.",
        "image_url": "https://image.tmdb.org/t/p/w500/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg"
      },
      {
        "name": "Emilia Pérez",
        "release_year": 2024,
        "description": "A musical crime drama about a drug lord’s gender transition.",
        "image_url": "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg"
      },
      {
        "name": "The Apprentice",
        "release_year": 2024,
        "description": "A biographical drama about Donald Trump’s early years.",
        "image_url": "https://image.tmdb.org/t/p/w500/5P8SmMzSNYikXpxil6BYzJ16611.jpg"
      }
    ]
  },
  {
    "key": "action_movies",
    "movies": [
      {
        "name": "The Creator",
        "release_year": 2024,
        "description": "In a future war between humans and AI, a soldier finds a mysterious child with great power.",
        "image_url": "https://image.tmdb.org/t/p/w500/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg"
      },
      {
        "name": "John Wick: Chapter 4",
        "release_year": 2023,
        "description": "John Wick uncovers a path to defeating The High Table, but must face new enemies.",
        "image_url": "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"
      },
      {
        "name": "Extraction 2",
        "release_year": 2023,
        "description": "Tyler Rake returns for another high-stakes rescue mission.",
        "image_url": "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg"
      },
      {
        "name": "Fast X",
        "release_year": 2023,
        "description": "Dom Toretto and his crew face a deadly threat from the past.",
        "image_url": "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg"
      },
      {
        "name": "Rebel Moon – Part One",
        "release_year": 2023,
        "description": "A mysterious outsider becomes a beacon of hope against tyrannical forces.",
        "image_url": "https://image.tmdb.org/t/p/w500/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg"
      },
      {
        "name": "Napoleon",
        "release_year": 2023,
        "description": "An epic portrayal of Napoleon Bonaparte’s rise to power.",
        "image_url": "https://image.tmdb.org/t/p/w500/5P8SmMzSNYikXpxil6BYzJ16611.jpg"
      }
    ]
  },
  {
    "key": "comedy_movies",
    "movies": [
      {
        "name": "Barbie",
        "release_year": 2023,
        "description": "Barbie ventures into the real world, confronting modern expectations.",
        "image_url": "https://image.tmdb.org/t/p/w500/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg"
      },
      {
        "name": "Poor Things",
        "release_year": 2023,
        "description": "A woman brought back to life explores freedom and gender in a surreal world.",
        "image_url": "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg"
      },
      {
        "name": "No Hard Feelings",
        "release_year": 2023,
        "description": "A woman is hired to 'date' an awkward 19-year-old before he goes to college.",
        "image_url": "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"
      },
      {
        "name": "Strays",
        "release_year": 2023,
        "description": "An R-rated talking dog comedy about revenge and loyalty.",
        "image_url": "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg"
      },
      {
        "name": "The French Dispatch",
        "release_year": 2021,
        "description": "A love letter to journalists, told through quirky vignettes.",
        "image_url": "https://image.tmdb.org/t/p/w500/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg"
      },
      {
        "name": "Next Goal Wins",
        "release_year": 2023,
        "description": "A football coach tries to turn around the world's worst national soccer team.",
        "image_url": "https://image.tmdb.org/t/p/w500/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg"
      }
    ]
  },
  {
    "key": "oscar_nominated_movies",
    "movies": [
      {
        "name": "Oppenheimer",
        "release_year": 2023,
        "description": "The story of J. Robert Oppenheimer and the making of the atomic bomb.",
        "image_url": "https://image.tmdb.org/t/p/w500/5P8SmMzSNYikXpxil6BYzJ16611.jpg"
      },
      {
        "name": "Killers of the Flower Moon",
        "release_year": 2023,
        "description": "FBI investigates a string of murders among the Osage people.",
        "image_url": "https://image.tmdb.org/t/p/w500/vIgyYkXkg6NC2whRbYjBD7eb3Er.jpg"
      },
      {
        "name": "Maestro",
        "release_year": 2023,
        "description": "A biopic about composer Leonard Bernstein and his relationship with Felicia Montealegre.",
        "image_url": "https://image.tmdb.org/t/p/w500/fiVW06jE7z9YnO4trhaMEdclSiC.jpg"
      },
      {
        "name": "Anatomy of a Fall",
        "release_year": 2023,
        "description": "A woman is put on trial after her husband dies under suspicious circumstances.",
        "image_url": "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg"
      },
      {
        "name": "Past Lives",
        "release_year": 2023,
        "description": "Two childhood friends reconnect years later, reflecting on destiny and choices.",
        "image_url": "https://image.tmdb.org/t/p/w500/7BsvSuDQuoqhWmU2fL7W2GOcZHU.jpg"
      },
      {
        "name": "The Holdovers",
        "release_year": 2023,
        "description": "A cranky teacher is stuck at school over Christmas with a troubled student.",
        "image_url": "https://image.tmdb.org/t/p/w500/5P8SmMzSNYikXpxil6BYzJ16611.jpg"
      }
    ]
  },
  {
    "key": "animation_movies",
    "movies": [
      {
        "name": "Wish",
        "release_year": 2023,
        "description": "A young girl makes a powerful wish that threatens to upend her kingdom.",
        "image_url": "https://image.tmdb.org/t/p/w500/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg"
      },
      {
        "name": "The Boy and the Heron",
        "release_year": 2023,
        "description": "A fantasy journey from the legendary Studio Ghibli.",
        "image_url": "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg"
      },
      {
        "name": "Elemental",
        "release_year": 2023,
        "description": "In a city where fire-, water-, land-, and air-residents live together, a fiery young woman and a go-with-the-flow guy discover something elemental: how much they have in common.",
        "image_url": "https://image.tmdb.org/t/p/w500/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg"
      }
    ]
  }
]
""".trimIndent()



    fun parseMovieJson(jsonString: String): List<MovieResponseItem> {
        val type = object : TypeToken<List<MovieResponseItem>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
}