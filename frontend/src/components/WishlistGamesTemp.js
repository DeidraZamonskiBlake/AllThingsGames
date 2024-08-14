import { useState, useEffect } from "react";
import { fetchLoggedInUsersWishlistedGames } from "./LoggedInUserData";
import { Link } from "react-router-dom";
import PopularGamesImg from "./PopularGamesImg";

function WishlistGamesTemp() {
  const [wishlistGames, setWishlistGames] = useState([]);
  const loggedInEmail = useState(localStorage.getItem("loggedInEmail"));

  useEffect(() => {
    const fetchLoggedInUserData = async () => {
      try {
        const loggedInUsersWishlistedGames =
          await fetchLoggedInUsersWishlistedGames();
        setWishlistGames(loggedInUsersWishlistedGames);
      } catch (error) {
        console.log(
          "There was a problem fetching the logged-in user's wishlisted games: " +
            error
        );
      }
    };

    fetchLoggedInUserData();
  }, [wishlistGames]);

  return (
    <div>
      <div>
        <h3>{loggedInEmail[0]}'s Wishlisted Games</h3>
      </div>
      <ul>
        {wishlistGames.map((wishlistGame) => (
          <li key={wishlistGame.id}>
            <Link to={`/GameInfoPage/${wishlistGame.itadId}`}>
              <PopularGamesImg game={wishlistGame} />
              <h3>{wishlistGame.title}</h3>
            </Link>
            <h4>Best Price</h4>
            <div>
              <span>$</span>
              <span id={`game-price-${wishlistGame.itadId}`}>
                {wishlistGame.priceWhenAdded}
              </span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default WishlistGamesTemp;
