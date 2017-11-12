package pkgCore;

import java.util.ArrayList;
import java.util.Collections;

import pkgEnum.eCardNo;
import pkgEnum.eHandStrength;
import pkgEnum.eRank;
import pkgEnum.eSuit;

public class HandPoker extends Hand {

	private ArrayList<CardRankCount> CRC = null;

	public HandPoker() {
		this.setHS(new HandScorePoker());
	}

	protected ArrayList<CardRankCount> getCRC() {
		return CRC;
	}

	@Override
	public HandScore ScoreHand() {
		// Implement this method... call each of the 'is' methods (isRoyalFlush,
		// etc) until
		// one of the hands is true, then score the hand
		
		HandScorePoker hs = new HandScorePoker();
		
		if (this.isRoyalFlush()){

			hs.seteHandStrength(eHandStrength.RoyalFlush);

		}

		else if (this.isStraightFlush()){

		hs.seteHandStrength(eHandStrength.StraightFlush);

		}

		else if (this.isFourOfAKind()){

		hs.seteHandStrength(eHandStrength.FourOfAKind);

		}

		else if (this.isFullHouse()){

		hs.seteHandStrength(eHandStrength.FullHouse);

		}

		else if (this.isFlush()){

		hs.seteHandStrength(eHandStrength.Flush);

		}

		else if (this.isStraight()){

		hs.seteHandStrength(eHandStrength.Straight);

		}

		else if (this.isThreeOfAKind()){

		hs.seteHandStrength(eHandStrength.ThreeOfAKind);

		}

		else if (this.isTwoPair()){

		hs.seteHandStrength(eHandStrength.TwoPair);

		}

		else if (this.isPair()){

		hs.seteHandStrength(eHandStrength.Pair);

		}

		else{

		hs.seteHandStrength(eHandStrength.HighCard);

		}
	
		Collections.sort(super.getCards());
		Frequency();

		if (isRoyalFlush()) {

		} else if (isStraightFlush()) {

		}

		return null;
	}

	private void Frequency() {

		CRC = new ArrayList<CardRankCount>();

		int iCnt = 0;
		int iPos = 0;

		for (eRank eRank : eRank.values()) {
			iCnt = (CountRank(eRank));
			if (iCnt > 0) {
				iPos = FindCardRank(eRank);
				CRC.add(new CardRankCount(eRank, iCnt, iPos));
			}
		}

		Collections.sort(CRC);

		for (CardRankCount crcount : CRC) {
			System.out.print(crcount.getiCnt());
			System.out.print(" ");
			System.out.print(crcount.geteRank());
			System.out.print(" ");
			System.out.println(crcount.getiCardPosition());
		}

	}

	private int CountRank(eRank eRank) {
		int iCnt = 0;
		for (Card c : super.getCards()) {
			if (c.geteRank() == eRank) {
				iCnt++;
			}
		}
		return iCnt;
	}

	private int FindCardRank(eRank eRank) {
		int iPos = 0;

		for (iPos = 0; iPos < super.getCards().size(); iPos++) {
			if (super.getCards().get(iPos).geteRank() == eRank) {
				break;
			}
		}
		return iPos;
	}

	public boolean isRoyalFlush() {
		boolean bIsRoyalFlush = false;
		// TODO : Implement this method
		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
			      				
		}

		return bIsRoyalFlush;
	}

	public boolean isStraightFlush() {
		boolean bisStraightFlush = false;
		// TODO : Implement this method
		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;
		
		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}
		if (iSuitCnt == iCardCnt){
			for (int x=0; x<5; ++x) {
				int rank = super.getCards().get(x).geteRank().getiRankNbr();
					if (rank + 1 == super.getCards().get(x+1).geteRank().getiRankNbr()) {
						bisStraightFlush = true;
					}
					else {
						bisStraightFlush = false;
					}
				}
			}	
			else {
				bisStraightFlush = false;
			}
			return bisStraightFlush;
	}

	public boolean isFourOfAKind() {
		boolean bisFourOfAKind = false;
		HandScorePoker HS = (HandScorePoker) super.getHS();

		if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == super.getCards()
				.get(eCardNo.FOURTH.getiCardNo()).geteRank()) {

			HS.seteHandStrength(eHandStrength.FourOfAKind);
			HS.setHiCard(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setLoCard(null);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIFTH.getiCardNo()));
			HS.setKickers(kickers);
			bisFourOfAKind = true;

		} else if (super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank() == super.getCards()
				.get(eCardNo.FIFTH.getiCardNo()).geteRank()) {
			HS.seteHandStrength(eHandStrength.FourOfAKind);
			HS.setHiCard(super.getCards().get(eCardNo.SECOND.getiCardNo()));
			HS.setLoCard(null);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setKickers(kickers);
			bisFourOfAKind = true;
		}

		return bisFourOfAKind;
	}

	public boolean isFullHouse() {
		boolean bisFullHouse = false;

		if (this.CRC.size() == 2) {
			if ((CRC.get(0).getiCnt() == 3) && (CRC.get(1).getiCnt() == 2)) {
				bisFullHouse = true;
				HandScorePoker HSP = (HandScorePoker) this.getHS();
				HSP.seteHandStrength(eHandStrength.FullHouse);
				HSP.setHiCard(this.getCards().get(CRC.get(0).getiCardPosition()));
				HSP.setLoCard(this.getCards().get(CRC.get(1).getiCardPosition()));
				ArrayList<Card> kickers = new ArrayList<Card>();
				HSP.setKickers(kickers);
				this.setHS(HSP);
			}
			
		}
		return bisFullHouse;

	}

	public boolean isFlush() {
		boolean bisFlush = false;

		int iCardCnt = super.getCards().size();
		int iSuitCnt = 0;

		for (eSuit eSuit : eSuit.values()) {
			for (Card c : super.getCards()) {
				if (eSuit == c.geteSuit()) {
					iSuitCnt++;
				}
			}
			if (iSuitCnt > 0)
				break;
		}

		if (iSuitCnt == iCardCnt)
			bisFlush = true;
		else
			bisFlush = false;

		return bisFlush;
	}

	public boolean isStraight() {
		boolean bisStraight = false;
		//TODO : Implement this method
		return bisStraight;
	}

	public boolean isThreeOfAKind() {
		boolean bisThreeOfAKind = false;

		if (this.CRC.size() == 3) {
			if ((CRC.get(0).getiCnt() == 3)){

				HandScorePoker HSP = (HandScorePoker)this.getHS();
				HSP.seteHandStrength(eHandStrength.ThreeOfAKind);
				
				
				int iGetCard = this.getCRC().get(0).getiCardPosition();
				
				HSP.setHiCard(this.getCards().get(iGetCard));
				HSP.setLoCard(null);
				
				HSP.setKickers(FindTheKickers(this.getCRC()));

				
				this.setHS(HSP);
				
			}
		}

		return bisThreeOfAKind;
	}
	
	private ArrayList<Card> FindTheKickers(ArrayList<CardRankCount> CRC)
	{
		ArrayList<Card> kickers = new ArrayList<Card>();
		
		for (CardRankCount crcCheck: CRC)
		{
			if (crcCheck.getiCnt() == 1)
			{
				kickers.add(this.getCards().get(crcCheck.getiCardPosition()));
			}
		}
		
		return kickers;
	}

	public boolean isTwoPair() {
		boolean bisTwoPair = false;
		HandScorePoker HS = (HandScorePoker) super.getHS();
		
		if (super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank() == super.getCards().
				get(eCardNo.THIRD.getiCardNo()).geteRank() &&super.getCards().
				get(eCardNo.FOURTH.getiCardNo()).geteRank() ==super.getCards().
				get(eCardNo.FIFTH.getiCardNo()).geteRank()) {
			bisTwoPair = true;
			HS.setHiCard(super.getCards().get(eCardNo.SECOND.getiCardNo()));
			HS.setLoCard(super.getCards().get(eCardNo.FOURTH.getiCardNo()));
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setKickers(kickers);
			
	
		}else if(super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == super.getCards().
				get(eCardNo.SECOND.getiCardNo()).geteRank() && super.getCards().get(eCardNo.THIRD.getiCardNo()).
				geteRank() ==super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank()) {
			HS.seteHandStrength(eHandStrength.TwoPair);
			    HS.setHiCard(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setLoCard(super.getCards().get(eCardNo.THIRD.getiCardNo()));
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIFTH.getiCardNo()));
			HS.setKickers(kickers);
			bisTwoPair = true;
		}else if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() == 
				
				
				super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank() && 
				super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank() == super.getCards().
				get(eCardNo.FIFTH.getiCardNo()).geteRank()) {
			bisTwoPair = true;
			HS.setHiCard(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setLoCard(super.getCards().get(eCardNo.FOURTH.getiCardNo()));
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.THIRD.getiCardNo()));
			HS.setKickers(kickers);
			
		}
		return bisTwoPair;
		
	}


	public boolean isPair() {
		boolean bisPair = false;
		HandScorePoker HS = (HandScorePoker)super.getHS();
		Card A = super.getCards().get(eCardNo.FIRST.getiCardNo());
		Card B = super.getCards().get(eCardNo.SECOND.getiCardNo());
		Card C = super.getCards().get(eCardNo.THIRD.getiCardNo());
		Card D = super.getCards().get(eCardNo.FOURTH.getiCardNo());
		Card E = super.getCards().get(eCardNo.FIFTH.getiCardNo());
		
		if (super.getCards().get(eCardNo.FIRST.getiCardNo()).geteRank() ==
				super.getCards().get(eCardNo.SECOND.getiCardNo()).geteRank()) {
			HS.seteHandStrength(eHandStrength.Pair);
			HS.setHiCard(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			HS.setLoCard(null);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.THIRD.getiCardNo()));
			kickers.add(super.getCards().get(eCardNo.FOURTH.getiCardNo()));
			kickers.add(super.getCards().get(eCardNo.FIFTH.getiCardNo()));
			HS.setKickers(kickers);
			bisPair = true;
		
		}else if (B.geteRank().getiRankNbr() == C.geteRank().getiRankNbr()) {
			bisPair = true;
			HS.seteHandStrength(eHandStrength.Pair);
			HS.setHiCard(B);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(A);
			HS.setKickers(kickers);
			
		}else if (super.getCards().get(eCardNo.THIRD.getiCardNo()).geteRank() ==
				super.getCards().get(eCardNo.FOURTH.getiCardNo()).geteRank()) {
			HS.seteHandStrength(eHandStrength.Pair);
			HS.setHiCard(super.getCards().get(eCardNo.THIRD.getiCardNo()));
			HS.setLoCard(null);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(super.getCards().get(eCardNo.FIRST.getiCardNo()));
			kickers.add(super.getCards().get(eCardNo.SECOND.getiCardNo()));
			kickers.add(super.getCards().get(eCardNo.FIFTH.getiCardNo()));
			HS.setKickers(kickers);
			bisPair = true;
		}else if (D.geteRank().getiRankNbr() == E.geteRank().getiRankNbr()){
			bisPair = true;
			HS.seteHandStrength(eHandStrength.Pair);
			HS.setHiCard(D);
			ArrayList<Card> kickers = new ArrayList<Card>();
			kickers.add(A);
			HS.setKickers(kickers);
		}
		this.setHS(HS);
		return bisPair;
	}

	public boolean isHighCard() {
		boolean bisHighCard = false;
		HandScorePoker HSP = (HandScorePoker)this.getHS();
		HSP.seteHandStrength(eHandStrength.HighCard);				
		HSP.setHiCard(this.getCards().get(this.getCRC().get(0).getiCardPosition()));
		HSP.setLoCard(null);	
		this.getCRC().remove(0);
		HSP.setKickers(FindTheKickers(this.getCRC()));
		this.setHS(HSP);
		bisHighCard=true;
		
		return bisHighCard;
	}
}
